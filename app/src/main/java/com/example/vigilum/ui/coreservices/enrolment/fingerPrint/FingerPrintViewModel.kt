package com.example.vigilum.ui.coreservices.enrolment.fingerPrint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.com.aratek.fp.Bione
import cn.com.aratek.fp.FingerprintImage
import cn.com.aratek.fp.FingerprintScanner
import com.example.vigilum.data.remote.response.FingerprintData
import com.example.vigilum.data.repository.FingerprintRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FingerPrintViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fingerprintRepository: FingerprintRepository
) : ViewModel() {

    val FB_DB_PATH = "/sdcard/fp.db"

    //lateinit var fScanner: FingerprintScanner


    lateinit var fi: FingerprintImage
    var Status_code: Int = 0
    lateinit var image: Any
    private var _fpImage = MutableStateFlow<Bitmap?>(null)
    var fpImage = _fpImage.asStateFlow()

    private val _isStartTriggered = MutableStateFlow(false)
    val isStartTriggered = _isStartTriggered.asStateFlow()


    // Initialize your MutableStateFlow with an initial value
    private var _scoreQuality = MutableStateFlow(0)

    // Expose an immutable version of the state flow to the UI layer
    val scoreQuality = _scoreQuality.asStateFlow()

    private var _scoreQual1 = MutableStateFlow(listOf<Int>())
    val scoreQual1: StateFlow<List<Int>> get() = _scoreQual1.asStateFlow()


    // Initialize your MutableStateFlow with an initial value
    private var _numImgTaken = MutableStateFlow(0)

    // Expose an immutable version of the state flow to the UI layer
    val numImgTaken = _numImgTaken.asStateFlow()

    // Initialize your MutableStateFlow with an initial value
    private var _changeFingerCounter = MutableStateFlow(0)

    // Expose an immutable version of the state flow to the UI layer
    val changeFingerCounter = _changeFingerCounter.asStateFlow()


    val images = mutableListOf<FingerprintImage>()
    val FPFeatures = mutableListOf<ByteArray>()

    //lateinit var FPTemplate : ByteArray
    lateinit var FPTemplate: ByteArray // Array to hold  FPTemplates


    // State to track if the user has allowed to continue capturing
    private val _canCaptureMore = MutableLiveData<Boolean>(false)
    val canCaptureMore: LiveData<Boolean> = _canCaptureMore

    // Call this function when the button is pressed
    fun onContinueButtonPressed() {
        _canCaptureMore.value = true
    }

    fun test() {
        Log.i(TAG, "goat")

        Log.i(TAG, "${_isStartTriggered.value}")

    }

    fun triggerStart() {
        Log.i("TWG", "${_isStartTriggered.value}")

        _isStartTriggered.value = true
        Log.i("TWG", "${_isStartTriggered.value}")
    }
    private val _submissionStatus = MutableStateFlow(false)
    val submissionStatus: StateFlow<Boolean> get() = _submissionStatus


    //open FP scanner on init
    init {
        Log.i(TAG, "Init Open FP")
        openFP()
    }

    fun openFP() {
        Log.i(TAG, "Entered Open FP")
        try {
            Log.i(TAG, "Entered try block")

            val mScanner = FingerprintScanner.getInstance(context)

            var error: Int
            error = mScanner.open()
            Status_code = error
            if (error != FingerprintScanner.RESULT_OK) {
                Log.i(TAG, "Open Unsuccessful with error code: ${error}")

            } else {
                Log.i(TAG, "Open Successful")

            }

        } catch (e: Exception) {
            Log.i(TAG, "Exception is : ${e}")

        }
        Log.i(TAG, "Exit Open FP")

    }

    fun shutdownFP() {
        val mScanner = FingerprintScanner.getInstance(context)

        var error: Int
        // error = fScanner.open()
        error = mScanner.open()
        Status_code = error

        if (error != FingerprintScanner.RESULT_OK) {
            Log.i(TAG, "Closed Unsuccessful with error code: ${error}")

        } else {
            Log.i(TAG, "Closed Successful")

        }
    }


    fun obtainThreeFPImages() {
        Log.i(TAG, "Entered 3 FP images")

        _isStartTriggered.value = true
        try {


            val mScanner = FingerprintScanner.getInstance(context)
            var attempts = 0
            var changeFingerCounter = 0
            val startTime = System.currentTimeMillis()

            viewModelScope.launch(Dispatchers.IO) { // Run in background thread
                while (images.size < 3) {
                    if (System.currentTimeMillis() - startTime > 300_000) {
                        withContext(Dispatchers.Main) {
                            Log.i(TAG, "Timeout reached before capturing 3 images")
                            _isStartTriggered.value = false
                        }
                        break
                    }

                    try {
                        mScanner.prepare()
                        val res = mScanner.capture()
                        mScanner.finish()

                        if (res.error != FingerprintScanner.RESULT_OK) {
                            Log.i(TAG, "Unsuccessful with error code: ${res.error}")
                            attempts++
                            continue
                        }
                        val fi = res.data as FingerprintImage
                        //check whether FP quality > 70
                        if (Bione.getFingerprintQuality(fi) > 50) {
                            images.add(fi)

                            if (images.size % 3 == 0) {
                                changeFingerCounter++
                            }

                            withContext(Dispatchers.Main) { // Update UI thread
                                _numImgTaken.emit(images.size)
                                _changeFingerCounter.value = changeFingerCounter

                                _scoreQual1.value =
                                    _scoreQual1.value + Bione.getFingerprintQuality(fi)
                                //call function to update displayed finger image
                                _fpImage.emit(ByteArrayToBitmap(fi.convert2Bmp()))
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e(TAG, "An error occurred during fingerprint capture: ${e.message}")
                        }
                        attempts++
                    }
                }

                withContext(Dispatchers.Main) {
                    if (images.size < 3) {
                        Log.i(
                            TAG,
                            "Could not capture three images after $attempts attempts and within the timeout period"
                        )
                    } else {
                        Log.i(TAG, "Successfully captured three images")
                    }
                }
            }
        }catch (e:NoClassDefFoundError){
            Log.i(TAG, "NoClassDefFoundError exception raised")
        }
    }

    fun cancelObtainThreeFPImages() {
        //empty FPimage list
        images.clear()

        //close dialog
        _isStartTriggered.value = false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onSubmitClick() {
        Log.i("ENTERED_SUBMIT", "ENTERED_SUBMIT")

        _isStartTriggered.value = false

        viewModelScope.launch {
            val template = extractFPFeatureAndCreateTemplate()
            // Use the template here
            val templateEncodedString = Base64.encodeToString(template, Base64.DEFAULT)
            Log.i("TEMPLATE", "${templateEncodedString}")
            var fingerprintData = FingerprintData("1",templateEncodedString)
            //submit to server
            var response =  fingerprintRepository.postFingerprint(fingerprintData)
            Log.i("Response TEMPLATE", "${response}")

            if (response.status == "200"){
                _submissionStatus.value = true

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun extractFPFeatureAndCreateTemplate(): ByteArray {
        val numIteration = images.size
        var counter = 0
        while (counter < numIteration) {
            val res = Bione.extractFeature(images[counter])
            Status_code = res.error
            if (res.error != Bione.RESULT_OK) {
                Log.i(TAG, "enroll_failed_because_of_extract_feature " + res.error)
                throw Exception("Feature extraction failed with error code: ${res.error}")
            }
            FPFeatures.add(res.data as ByteArray)
            counter++
        }
        Log.i(TAG, "success_extract_features " + FPFeatures.size)
        return makeOneTemplates(FPFeatures)
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun onSubmitClick() {
        //obtain FPFeatures from FPTemplate
        extractFPFeature()
        //obtain FPTemplate from FP features

        //close dialog
        _isStartTriggered.value = false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun extractFPFeatureAndCreateTemplate():ByteArray {
        viewModelScope.launch(Dispatchers.Main) { // Confine to Main thread
            val numIteration = images.size
            var counter = 0
            while (counter < numIteration) {
                val res = Bione.extractFeature(images[counter])
                Status_code = res.error
                if (res.error != Bione.RESULT_OK) {
                    Log.i(TAG, "enroll_failed_because_of_extract_feature " + res.error)
                    return@launch
                }
                FPFeatures.add(res.data as ByteArray)
                counter++
            }
            Log.i(TAG, "success_extract_features " + FPFeatures.size)
            val template = makeOneTemplates(FPFeatures)

        }

    }*/


    fun makeOneTemplates(fpFeatures: MutableList<ByteArray>): ByteArray {
        if (fpFeatures.size != 3) {
            Log.i("FPTEMPLATE", "fp Features array must contain exactly 3 elements")
        }

        val res = Bione.makeTemplate(fpFeatures[0], fpFeatures[1], fpFeatures[2])
        if (res.error != Bione.RESULT_OK) {
            Log.i("FPTEMPLATE", "enroll_failed_because_of_make_template: " + res.error)
        }

        FPTemplate = res.data as ByteArray
        return FPTemplate

    }


    fun ByteArrayToBitmap(fpImage: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(fpImage, 0, fpImage.size)
    }


    companion object {
        val TAG = "FP_STATUS"
    }


}