package com.example.vigilum

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adsnetkyc.presentation.qouikscreens.adviceBeforeEnrolment.AdviceBeforeEnrolmentScreen
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.adsnetkyc.presentation.qouikscreens.enrollSteps.Selfie.ProfilePictureViewModel
import com.example.vigilum.ui.coreservices.enrolment.fingerPrint.FingerPrintViewModel
import com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod.ChooseAnOperationScreen
import com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod.ChooseAnOperationViewModel
import com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod.SelectIdentificationMethodScreen
import com.example.adsnetkyc.presentation.qouikscreens.selectIdentificationMethod.SelectIdentificationMethodViewModel
import com.example.vigilum.data.repository.RemoteConfigurationRepository
import com.example.vigilum.domain.model.DrawerMenuItem
import com.example.vigilum.ui.components.generalComponents.Footer
import com.example.vigilum.ui.components.generalComponents.SuccessScreenComposable
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.PersonalInfoScreen
import com.example.vigilum.ui.coreservices.enrolment.PersonalInfo.PersonalInfoViewModel
import com.example.vigilum.ui.coreservices.enrolment.Selfie.ProfilePictureScreen
import com.example.vigilum.ui.coreservices.enrolment.Signature.BeforeContractScreen
import com.example.vigilum.ui.coreservices.enrolment.Signature.ContractDisplayScreen
import com.example.vigilum.ui.coreservices.enrolment.Signature.SignatureCollectScreen
import com.example.vigilum.ui.coreservices.enrolment.Signature.SignatureViewModel
import com.example.vigilum.ui.coreservices.enrolment.adviceBeforeEnrolment.AdviceBeforeEnrolmentViewModel
import com.example.vigilum.ui.coreservices.enrolment.fingerPrint.FingerPrintScreen
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.SelectDocType.SelectDocTypeScreen
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.SelectDocType.SelectDocTypeViewModel
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.recto.IdDocImageScanRectoViewModel
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.recto.IdDocImageScanScreenRecto
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.verso.IdDocImageScanScreenVerso
import com.example.vigilum.ui.coreservices.enrolment.idDocImage.verso.IdDocImageScanVersoViewModel

import com.example.vigilum.ui.home.HomeScreen
import com.example.vigilum.ui.home.HomeViewModel
import com.example.vigilum.ui.remoteconfiguration.ProcesingRemoteConfigurationScreen
import com.example.vigilum.ui.remoteconfiguration.ProcesingRemoteConfigurationViewModel
import com.example.vigilum.ui.remoteconfiguration.RemoteConfigurationScreen
import com.example.vigilum.ui.remoteconfiguration.RemoteConfigurationViewModel
import com.example.vigilum.ui.remoteconfiguration.SuccessfulRemoteConfigurationScreen
import com.example.vigilum.ui.theme.ShopTheme
import com.example.vigilum.utils.Destinations
import com.example.vigilum.utils.checkFirstLaunch
import com.example.vigilum.utils.checkRemoteConfigExist
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var remoteConfigurationRepository: RemoteConfigurationRepository // Inject the repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ShopTheme {

                val navController = rememberNavController()
                // Check if it's the first launch
                val isFirstLaunch = checkFirstLaunch(this)
                //Check if a remote config is present in db
                val isRemoteConfigExist = checkRemoteConfigExist(remoteConfigurationRepository);

                // Set start destination based on whether it's the first launch and remote config exist
                val startDestination = if (isFirstLaunch) {
                    Destinations.RemoteConfigurationScreen.toString()
                } else if (!isFirstLaunch && !isRemoteConfigExist) {
                    Destinations.RemoteConfigurationScreen.toString()

                } else {
                    Destinations.HomeScreen.toString()
                }

                //Navigation
                NavHost(navController = navController, startDestination = startDestination) {

                    //NavGraphBuilder: add the destination to the nav graph
                    composable(route = Destinations.HomeScreen.toString()) {
                        MainUi(navController)
                    }
                    //remote config screen
                    composable(route = Destinations.RemoteConfigurationScreen.toString()) {
                        val remoteConfigurationViewModel: RemoteConfigurationViewModel =
                            hiltViewModel()

                        RemoteConfigurationScreen(remoteConfigurationViewModel, navController)
                    }

                    composable(
                        route = Destinations.ProcessingRemoteConfigurationScreen.toString() + "/{serverIp}/{merchantCode}/{securityKey}",
                        arguments = listOf(

                            navArgument("serverIp") {
                                type = NavType.StringType; defaultValue = ""
                            },
                            navArgument("merchantCode") {
                                type = NavType.StringType; defaultValue = ""
                            },
                            navArgument("securityKey") {
                                type = NavType.StringType; defaultValue = ""
                            },

                            )
                    ) { backStackEntry ->
                        val serverIp = backStackEntry.arguments?.getString("serverIp", "")
                        val merchantCode = backStackEntry.arguments?.getString("merchantCode", "")
                        val securityKey = backStackEntry.arguments?.getString("securityKey", "")
                        val procesingRemoteConfigurationViewModel: ProcesingRemoteConfigurationViewModel =
                            hiltViewModel()

                        ProcesingRemoteConfigurationScreen(
                            procesingRemoteConfigurationViewModel,
                            navController,
                            serverIp!!,
                            merchantCode!!,
                            securityKey!!
                        )
                    }

                    //successful config screen
                    composable(route = Destinations.SuccessfulRemoteConfigurationScreen.toString()) {
                        SuccessfulRemoteConfigurationScreen(navController)
                    }

                    //1. choose an operation  screen
                    composable(route = Destinations.ChooseAnOperationScreen.toString() + "/{selectedLanguage}",
                        arguments = listOf(navArgument("selectedLanguage") {
                            type = NavType.StringType
                            defaultValue = "fr"

                        })
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val chooseAnOperationViewModel: ChooseAnOperationViewModel = hiltViewModel()
                        ChooseAnOperationScreen(
                            chooseAnOperationViewModel, navController, selectedLanguage
                        )
                    }

                    //2. A select identification method (phone, doc, fingerprint)
                    composable(
                        route = Destinations.SelectIdentificationMethodScreen.toString() + "/{selectedLanguage}",
                        arguments = listOf(navArgument("selectedLanguage") {
                            type = NavType.StringType
                            defaultValue = "fr"

                        })
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val selectIdentificationMethodViewModel: SelectIdentificationMethodViewModel =
                            hiltViewModel()
                        SelectIdentificationMethodScreen(
                            selectIdentificationMethodViewModel, navController, selectedLanguage
                        )
                    }

                    //2. B Before enrolment begins
                    composable(
                        route = Destinations.AdviceBeforeEnrolment.toString() + "/{selectedLanguage}",
                        arguments = listOf(navArgument("selectedLanguage") {
                            type = NavType.StringType
                            defaultValue = "fr"

                        })
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val adviceBeforeEnrolmentViewModel: AdviceBeforeEnrolmentViewModel =
                            hiltViewModel()
                        AdviceBeforeEnrolmentScreen(
                            adviceBeforeEnrolmentViewModel, navController, selectedLanguage
                        )
                    }

                    //3.  select document type
                    composable(
                        route = Destinations.SelectDocType.route + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        val selectDocTypeViewModel: SelectDocTypeViewModel = hiltViewModel()
                        SelectDocTypeScreen(
                            selectDocTypeViewModel,
                            selectedLanguage,
                            navController
                        )
                    }

                    //4. idDocScanRecto
                    composable(
                        route = Destinations.IdDocImageScanRecto.route + "/{selectedLanguage}" + "/{selectedDocType}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"
                            },
                            navArgument("selectedDocType") {
                                type = NavType.StringType; defaultValue = "NEW_NATIONAL_ID"
                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        val selectedDocType = it.arguments?.getString("selectedDocType", "NEW_NATIONAL_ID")!!
                        val activity = LocalContext.current as Activity

                        val idDocImageScanRectoViewModel: IdDocImageScanRectoViewModel =
                            hiltViewModel()
                        IdDocImageScanScreenRecto(
                            idDocImageScanRectoViewModel,
                            activity,
                            selectedLanguage,
                            selectedDocType,
                            navController
                        )
                    }
                    //5. idDocScanVerso
                    composable(
                        route = Destinations.IdDocImageScanVerso.route + "/{selectedLanguage}" + "/{selectedDocType}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                            navArgument("selectedDocType") {
                                type = NavType.StringType; defaultValue = "NEW_NATIONAL_ID"
                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        val selectedDocType =
                            it.arguments?.getString("selectedDocType", "NEW_NATIONAL_ID")!!
                        val activity = LocalContext.current as Activity

                        val idDocImageScanVersoViewModel: IdDocImageScanVersoViewModel =
                            hiltViewModel()
                        IdDocImageScanScreenVerso(
                            idDocImageScanVersoViewModel,
                            activity,
                            selectedLanguage,
                            selectedDocType,
                            navController
                        )
                    }

                    //6. Profile picture
                    composable(
                        route = Destinations.ProfilePicture.route + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        val activity = LocalContext.current as Activity

                        val profilePictureViewModel: ProfilePictureViewModel = hiltViewModel()
                        ProfilePictureScreen(
                            profilePictureViewModel,
                            activity,
                            selectedLanguage,
                            navController
                        )
                    }

                    //7. Visitor Info
                    composable(
                        route = Destinations.PersonalInfo.route + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val visitorInfoViewModel: PersonalInfoViewModel = hiltViewModel()
                        PersonalInfoScreen(
                            visitorInfoViewModel,
                            selectedLanguage,
                            navController
                        )
                    }

                    //8. Fingerprint collect
                    composable(
                        route = Destinations.FingerPrint.toString() + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val fingerPrintViewModel: FingerPrintViewModel = hiltViewModel()
                        FingerPrintScreen(
                            fingerPrintViewModel,
                            selectedLanguage,
                            navController
                        )
                    }

                    //13 before contract
                    composable(route = Destinations.ContractDisplayScreen.route.toString()+ "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        BeforeContractScreen(navController, selectedLanguage)
                    }

                    composable(
                        route = Destinations.ContractDisplayScreen.route.toString() + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!
                        val context = LocalContext.current
                        val application = context.applicationContext as Application
                        ContractDisplayScreen(application, selectedLanguage, navController)
                    }
                    composable(
                        route = Destinations.Signature.route + "/{selectedLanguage}",
                        arguments = listOf(
                            navArgument("selectedLanguage") {
                                type = NavType.StringType
                                defaultValue = "fr"

                            },
                        )
                    ) {
                        val selectedLanguage = it.arguments?.getString("selectedLanguage", "fr")!!

                        val signatureViewModel: SignatureViewModel = hiltViewModel()
                        SignatureCollectScreen(
                            signatureViewModel,
                            selectedLanguage,
                            navController
                        )
                    }


                    //success
                    composable(route = Destinations.SuccessScreen.route) {
                        SuccessScreenComposable(navController)
                    }

                }
            }
        }
    }


    fun arePermissionsGranted(): Boolean {
        return CAMERA_PERMISSION.all { permission ->
            ContextCompat.checkSelfPermission(
                applicationContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        val CAMERA_PERMISSION = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(navController: NavController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = hiltViewModel()


    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }


    Scaffold(topBar = {
        HeaderScreen()
    }, bottomBar = {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp)
                .fillMaxHeight(0.8f)
        ) {
            Footer()
        }
    }

    ) {
        HomeScreen(homeViewModel = homeViewModel, navController)

    }

}


fun PrepareBottomMenu(): List<DrawerMenuItem> {
    val buttomMenuItemList = listOf<DrawerMenuItem>(
        DrawerMenuItem(
            "Chat",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Message,
            Icons.Default.Message,
            false,
            3
        ),
        DrawerMenuItem(
            "Dashboard",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Dashboard,
            Icons.Default.Dashboard,
            false,
            null
        ),

        DrawerMenuItem(
            "Home",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Home,
            Icons.Default.Home,
            false,
            null
        ),

        DrawerMenuItem(
            "Blog",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Newspaper,
            Icons.Default.Newspaper,
            false,
            null
        ),
        DrawerMenuItem(
            "Jobs",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Work,
            Icons.Default.Work,
            true,
            12
        ),

        )
    return buttomMenuItemList
}

fun PrepareDrawerMenu(): List<DrawerMenuItem> {
    val drawerMenuItemList = listOf<DrawerMenuItem>(
        DrawerMenuItem(
            "Language",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Language,
            Icons.Default.Language,
            false,
            null
        ),
        DrawerMenuItem(
            "Settings",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Settings,
            Icons.Default.Settings,
            false,
            null
        ),

        DrawerMenuItem(
            "Premium",
            Destinations.HomeScreen.toString(),
            Icons.Filled.Diamond,
            Icons.Default.Diamond,
            false,
            null
        ),

        DrawerMenuItem(
            "Feedback",
            Destinations.HomeScreen.toString(),
            Icons.Filled.QuestionAnswer,
            Icons.Default.QuestionAnswer,
            true,
            2
        ),
        DrawerMenuItem(
            "FAQ",
            Destinations.HomeScreen.toString(),
            Icons.Filled.QuestionMark,
            Icons.Default.QuestionMark,
            true,
            null
        ),
        DrawerMenuItem(
            "Rate Us",
            Destinations.HomeScreen.toString(),
            Icons.Filled.RateReview,
            Icons.Default.RateReview,
            false,
            null
        ),

        )
    return drawerMenuItemList
}


