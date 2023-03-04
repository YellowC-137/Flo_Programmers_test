package yellowc.app.flo_clone.util


import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber

@BindingAdapter("loadImg")
fun loadImg(view: ImageView, imgUrl: String?) {
    Glide.with(view.context).load(imgUrl).into(view)
}

@BindingAdapter("onClickHideBottomSheet")
fun onClickHideBottomSheet(view: View, behavior: BottomSheetBehavior<out View>) {
    view.setOnClickListener {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}

@BindingAdapter("onClickShowBottomSheet")
fun onClickShowBottomSheet(view: View, behavior: BottomSheetBehavior<out View>) {
    view.setOnClickListener {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}





