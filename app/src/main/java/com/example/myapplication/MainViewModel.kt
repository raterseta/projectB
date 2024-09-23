package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val repository: ProfileLibrary): ViewModel() {

    private val _sortedProfile = MutableStateFlow(
        repository.getProfilesHead()

    )

    val sortedProfile : MutableStateFlow<List<ProfilesHead>> get() = _sortedProfile

}

class ViewModelFactory(private val repository: ProfileLibrary) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}