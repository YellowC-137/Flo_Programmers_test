package yellowc.app.flo_clone.ui.main


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import yellowc.app.flo_clone.R
import yellowc.app.flo_clone.databinding.ItemLyricBinding
import yellowc.app.flo_clone.domain.model.Lyric

class LyricAdapter(
    private val itemClicked: (Lyric) -> Unit
) : ListAdapter<Lyric, LyricAdapter.LyricViewHolder>(diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewHolder {
        return LyricViewHolder(
            ItemLyricBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LyricViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it, itemClicked)
        }
    }

    class LyricViewHolder(private val binding: ItemLyricBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Lyric,
            itemClicked: (Lyric) -> Unit
        ) = with(binding) {
            binding.lyric = item
            binding.root.setOnClickListener {
                itemClicked.invoke(item)
            }
            if (item.playing) {
                binding.tvLyric.setBackgroundResource(R.color.gray)
            } else {
                binding.tvLyric.setBackgroundResource(R.color.white)
            }

            executePendingBindings()
        }

    }

    companion object {
        private val diffutil = object : DiffUtil.ItemCallback<Lyric>() {
            override fun areItemsTheSame(
                oldItem: Lyric,
                newItem: Lyric
            ) = oldItem.start == newItem.start

            override fun areContentsTheSame(
                oldItem: Lyric,
                newItem: Lyric
            ) = oldItem == newItem
        }
    }
}

