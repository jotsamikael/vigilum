package com.example.vigilum.utils

sealed class Destinations (val route:String){
    object HomeScreen: Destinations("to_homescreen")
    object RemoteConfigurationScreen: Destinations("remoteconfigurationscreen")
    object ProcessingRemoteConfigurationScreen: Destinations("processing_remoteconfigurationscreen")
    object SuccessfulRemoteConfigurationScreen: Destinations("sucessful_remoteconfigurationscreen")
    object ChooseAnOperationScreen: Destinations("chooseanoperationscreen")
    object SelectIdentificationMethodScreen: Destinations("selectidentificationmethodscreen")

    object AdviceBeforeEnrolment: Destinations(route = "to_before_enrolment")
    object SelectDocType: Destinations(route = "to_select_doc_type")
    object IdDocImageScanRecto: Destinations(route = "to_id_doc_pic_recto")
    object IdDocImageScanVerso: Destinations(route = "to_id_doc_pic_verso")
    object ProfilePicture: Destinations(route = "to_profile_picture")

    object PersonalInfo: Destinations(route = "to_enrolment_form")

    object FingerPrint: Destinations(route = "to_fingerprint_collect")
    //object BeforeContractScreen : Destinations(route = "to_before_contract")
    object ContractDisplayScreen : Destinations(route = "to_contract_display")
    object Signature: Destinations(route = "to_signature_collect")

    object SuccessScreen: Destinations(route = "to_success")



}