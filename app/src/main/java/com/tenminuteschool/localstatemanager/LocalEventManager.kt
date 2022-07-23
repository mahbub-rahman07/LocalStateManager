package com.tenminuteschool.localstatemanager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow


const val TAG = "L_S_M_"

object LocalEventManager {
    /**
     * @eventSet will keep states
     * @dataMap will keep event data if any
     * */
    private val eventSet: MutableSet<State> = mutableSetOf()
    private val dataMap: MutableMap<State, Any> = mutableMapOf()

    /**
     * @eventStore is Mutable Live data type so that any whee can set data without lifecycle
     * @eventObserver is Flow type from @eventStore
     * */
    private val _eventStore = MutableLiveData<Boolean>()
    val eventObserver =  _eventStore.asFlow()

    fun addEvent(state: State, data: Any? = null) {
        eventSet.add(state)
        if (data != null) {
            dataMap[state] = data
        }
        _eventStore.value = true
    }

    private fun removeEvent(state: State) {
        eventSet.remove(state)
    }

    /**
     * @hasStateChanged method check if any event
     * return @Event
     * and remove state from @eventSet
     *
     * */
    fun hasStateChanged(state: State): Event {
        Log.d(TAG, "hasStateChanged: ${state.name}")
        if (eventSet.contains(state)) {
            removeEvent(state)
            return Event(state, true, getEventData(state))
        }
        return return Event(state, hasEvent = false)
    }
    /**
     * @getEventData method check if any data has that state
     * returns
     * and remove state from @dataMap
     *
     * */

    private fun getEventData(state: State): Any? {
        if (dataMap.containsKey(state)) {
            val data = dataMap[state]
            dataMap.remove(state)
            return data
        }
        return null
    }
    /**
     * @State will define as many state it's need in UI
     * */
    enum class State {
        DETAILS_VISITED, MORE_DETAILS_RESTART, MORE_DETAILS_HAS_VALUE
    }

    data class Event(
        val state: State,
        val hasEvent: Boolean,
        var data: Any? = null
    )
}