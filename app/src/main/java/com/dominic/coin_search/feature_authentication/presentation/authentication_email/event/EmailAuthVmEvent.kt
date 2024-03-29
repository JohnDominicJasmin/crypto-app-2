package com.dominic.coin_search.feature_authentication.presentation.authentication_email.event

sealed class EmailAuthVmEvent{

    object SendEmailVerification: EmailAuthVmEvent()
    object ResendEmailVerification: EmailAuthVmEvent()
    object RefreshEmail: EmailAuthVmEvent()
    object StartTimer: EmailAuthVmEvent()
    object SubscribeEmailVerification: EmailAuthVmEvent()

}
