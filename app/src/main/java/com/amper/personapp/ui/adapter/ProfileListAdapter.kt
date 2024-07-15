package com.amper.personapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.amper.personapp.R
import com.amper.personapp.data.model.PersonDto
import com.amper.personapp.databinding.ItemPersonShortDetailsBinding
import com.amper.personapp.ui.loadImage

class ProfileListAdapter(
    private var list: MutableList<PersonDto?> = mutableListOf(),
    private var listener: OnItemClickListener<PersonDto>?
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ITEM = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding by lazy {
            ItemPersonShortDetailsBinding.bind(itemView)
        }

        fun setupView(personDto: PersonDto?) = with(binding) {
            personDto?.let {
                it.picture?.medium?.let { url ->
                    imageProfile.transitionName = it.id
                    imageProfile.loadImage(url)
                }
                textProfileName.text =
                    root.context.getString(R.string.name_holder, it.firstName, it.lastName)
                textProfileEmail.text = it.email.orEmpty().ifEmpty { "N/A" }
                textPhone.text = it.mobileNumber.orEmpty().ifEmpty { "N/A" }
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutRes = if(viewType == VIEW_TYPE_ITEM){
            R.layout.item_person_short_details
        } else {
            R.layout.item_loading
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)

        return if(viewType == VIEW_TYPE_ITEM){
            ViewHolder(view)
        } else {
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING
        else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val person = list[position]
        if(holder is ViewHolder){
            holder.setupView(person)
            holder.itemView.setOnClickListener {
                person?.let { safePerson ->
                    listener?.onClick(safePerson, position)
                }
            }
        }
    }

    fun add(list: List<PersonDto>) {
        this.list.removeIf { it == null }
        this.list.addAll(list)
        this.list.distinctBy { it?.id }
        this.list.add(null)
        notifyItemRangeInserted(this.list.lastIndex, list.size)
    }

    fun isEmpty(): Boolean = list.filterNotNull().isEmpty()

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener<T> {
        fun onClick(item: T, position: Int)
    }

}