package sk.figlar.apodjc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import sk.figlar.apodjc.ApodRepository
import sk.figlar.apodjc.Result
import sk.figlar.apodjc.model.ApodApiModel

class ApodGalleryViewModel : ViewModel() {
    private val apodRepository = ApodRepository()

    val apods: MutableStateFlow<List<ApodApiModel>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            when (val result = apodRepository.getApodApiModels()) {
                is Result.Error   -> { }
                is Result.Success -> {
                    apods.value = result.data
                }
            }

        }
    }
}