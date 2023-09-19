package com.helpet.view.Books

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.BookDisease

class DiffCallback : DiffUtil.ItemCallback<BookDisease>() {
    override fun areItemsTheSame(oldItem: BookDisease, newItem: BookDisease): Boolean {
        return oldItem.name == newItem.name // 아이템이 같은지 여부를 비교합니다.
    }

    override fun areContentsTheSame(oldItem: BookDisease, newItem: BookDisease): Boolean {
        return oldItem == newItem // 아이템 내용이 같은지 여부를 비교합니다.
    }
}