package yellowc.app.flo_clone.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yellowc.app.flo_clone.domain.model.Music
import yellowc.app.flo_clone.domain.usecases.GetMusicUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val useCase: GetMusicUseCase) : ViewModel() {

    private val _music = MutableStateFlow<List<Music>>(emptyList())
    val music = _music.asStateFlow()

    fun getMusic() {
        viewModelScope.launch {
            _music.emit(listOf(useCase.invoke()))
        }
    }

}