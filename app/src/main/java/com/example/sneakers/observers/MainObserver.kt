package com.example.sneakers.observers

import com.example.sneakers.ui.main.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MainObserver {

    private val eventStream = MutableSharedFlow<MainEvent>()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun addToCart(product: Product?) {
        product ?: return
        sendEvent(MainEvent.AddToCart(product))
    }

    fun removeFromCart(product: Product?) {
        product ?: return
        sendEvent(MainEvent.RemoveFromCart(product))
    }

    fun openProductDetailFor(product: Product?) {
        product ?: return
        sendEvent(MainEvent.OpenProductDetail(product))
    }

    fun navUp() {
        sendEvent(MainEvent.NavUp)
    }

    fun proceedToCheckout() {
        sendEvent(MainEvent.ProceedToCheckout)
    }

    fun observeCartEvents(coroutineScope: CoroutineScope, listener: (MainEvent) -> Unit) {
        coroutineScope.launch {
            eventStream.collect {
                listener(it)
            }
        }
    }

    private fun sendEvent(event: MainEvent) {
        scope.launch {
            eventStream.emit(event)
        }
    }

    sealed class MainEvent {
        object ProceedToCheckout: MainEvent()

        object NavUp: MainEvent()

        class AddToCart(val product: Product): MainEvent()

        class RemoveFromCart(val product: Product): MainEvent()

        class OpenProductDetail(val product: Product): MainEvent()
    }
}