package com.task1.loadappfwd

sealed class ButtonState {
    object Loading : ButtonState()
    object Completed : ButtonState()
}