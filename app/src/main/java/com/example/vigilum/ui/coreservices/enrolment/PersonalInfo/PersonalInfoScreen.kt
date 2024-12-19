package com.example.vigilum.ui.coreservices.enrolment.PersonalInfo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adsnetkyc.presentation.qouikscreens.common.header.HeaderScreen
import com.example.vigilum.R
import com.example.vigilum.ui.components.HspacerComponent
import com.example.vigilum.ui.components.VspacerComponent
import com.example.vigilum.ui.components.generalComponents.RadioBtn
import com.example.vigilum.ui.components.generalComponents.RectagularButton
import com.example.vigilum.ui.theme.FormFieldLabel
import com.example.vigilum.ui.theme.primaryDark
import com.example.vigilum.ui.theme.primaryLight
import com.example.vigilum.utils.Destinations


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun PersonalInfoScreen(
    personalInfoViewModel: PersonalInfoViewModel = hiltViewModel(),
    selectedLanguage: String,
    navController: NavController
) {

    val state = personalInfoViewModel.state

    //when save is pressed
    LaunchedEffect(key1 = Unit) {
        personalInfoViewModel.submissionStatus.collect { isSuccess ->
            if (isSuccess) {
                //navController.navigate("to_contract_display/${selectedLanguage}")
                navController.navigate("${Destinations.FingerPrint.toString()}/${selectedLanguage}")
            }
        }
    }

    //radiobtn
    val selectedOption = remember { mutableStateOf("Male") }

    // State holder for scroll position
    val scrollState = rememberScrollState()


    // Define your background image
    val backgroundImage = painterResource(id = R.drawable.abstract_white_bg)
    Scaffold(
        topBar = { HeaderScreen() } // Use your Header here
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = backgroundImage,
                contentDescription = "Background Image",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                VspacerComponent(40)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                ) {
                    //form
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .verticalScroll(scrollState)
                    ) {
                        //first name
                        OutlinedTextField(
                            value = state.firstName,
                            onValueChange = {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.FirstnameChanged(it)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryLight,
                                focusedLabelColor = primaryLight,
                                cursorColor = FormFieldLabel,
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.AccountCircle,
                                    tint = FormFieldLabel, contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = "First name",
                                    color = FormFieldLabel,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            isError = state.firstNameError != null,
                            modifier = Modifier.fillMaxWidth(),

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        if (state.firstNameError != null) {
                            androidx.compose.material3.Text(
                                text = state.firstNameError,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                            )
                        }
                        VspacerComponent(5)

                        //last name
                        OutlinedTextField(
                            value = state.lastName, onValueChange = {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.LastnameChanged(it)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryDark,
                                focusedLabelColor = primaryLight,
                                cursorColor = FormFieldLabel,
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.AccountCircle,
                                    tint = FormFieldLabel, contentDescription = null
                                )
                            },
                            isError = state.lastNameError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Last name",
                                    color = FormFieldLabel,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        if (state.lastNameError != null) {
                            androidx.compose.material3.Text(
                                text = state.lastNameError,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                            )
                        }
                        VspacerComponent(5)

                        //Sex
                        RadioBtn(
                            selectedOption = selectedOption,
                            options = listOf("Male", "Female")
                        ) {
                            personalInfoViewModel.setSelectedGender(it)
                        }

                        VspacerComponent(5)
                        //phone
                        OutlinedTextField(
                            value = state.telephone!!, onValueChange = {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.TelephoneChanged(it)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryLight,
                                focusedLabelColor = primaryLight,
                                cursorColor = FormFieldLabel,
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.Call,
                                    tint = FormFieldLabel, contentDescription = null
                                )
                            },
                            isError = state.telephoneError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Tel",
                                    color = FormFieldLabel,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                        if (state.telephoneError != null) {
                            androidx.compose.material3.Text(
                                text = state.telephoneError,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                            )
                        }
                        VspacerComponent(5)
                        //Documement
                        OutlinedTextField(
                            value = state.nationalIDNumber!!, onValueChange = {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.NationalIDNumberChanged(it)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryLight,
                                focusedLabelColor = primaryLight,
                                cursorColor = FormFieldLabel,
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.AccountBox,
                                    tint = FormFieldLabel, contentDescription = null
                                )
                            },
                            isError = state.nationalIDNumberError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "DocumentPicture number",
                                    color = FormFieldLabel,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black


                            )
                        )
                        if (state.nationalIDNumberError != null) {
                            androidx.compose.material3.Text(
                                text = state.nationalIDNumberError,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                            )
                        }
                        VspacerComponent(5)
                        //Enterprise
                        OutlinedTextField(
                            value = state.enterprise!!, onValueChange = {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.EnterpriseChanged(it)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryLight,
                                focusedLabelColor = primaryLight,
                                cursorColor = FormFieldLabel,
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.AccountBox,
                                    tint = FormFieldLabel, contentDescription = null
                                )
                            },
                            isError = state.enterpriseError != null,
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Enterprise",
                                    color = FormFieldLabel,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black


                            )
                        )
                        if (state.enterpriseError != null) {
                            androidx.compose.material3.Text(
                                text = state.enterpriseError,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                            )
                        }

                        VspacerComponent(30)
                        //cancel and save buttons
                        Row(horizontalArrangement = Arrangement.End) {
                            RectagularButton(
                                160,
                                50,
                                "CANCEL",
                                primaryLight,
                                Icons.Rounded.Clear,
                                {
                                })
                            HspacerComponent(20)
                            RectagularButton(
                                150,
                                50,
                                "SAVE",
                                primaryLight,
                                Icons.Rounded.Check
                            ) {
                                personalInfoViewModel.onEvent(
                                    PersonalInfoFormEvent.Submit
                                )

                            }
                        }
                    }
                }

            }
        }
    }
}

