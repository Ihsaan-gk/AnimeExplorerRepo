package com.muhammedihsaan.animeexplorer.ui.animelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhammedihsaan.animeexplorer.databinding.ItemAnimeBinding
import com.muhammedihsaan.animeexplorer.domain.model.Anime

class AnimeListAdapter(
    private val onItemClick: (Anime) -> Unit
) : ListAdapter<Anime, AnimeListAdapter.AnimeVH>(Diff()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeVH {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeVH(binding)
    }

    override fun onBindViewHolder(holder: AnimeVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnimeVH(
        private val binding: ItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Anime) {
            binding.tvTitle.text = item.title
            binding.tvEpisodes.text = "Episodes: ${item.episodes ?: "N/A"}"
            binding.tvRating.text = "Rating: ${item.rating ?: "N/A"}"

            Glide.with(binding.imgPoster)
                .load(item.imageUrl)
                .into(binding.imgPoster)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(old: Anime, new: Anime) = old.id == new.id
        override fun areContentsTheSame(old: Anime, new: Anime) = old == new
    }
}