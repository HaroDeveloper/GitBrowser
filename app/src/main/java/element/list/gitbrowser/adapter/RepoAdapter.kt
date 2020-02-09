package element.list.gitbrowser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import element.list.gitbrowser.R
import element.list.gitbrowser.model.GitRepository
import element.list.gitbrowser.utils.format
import kotlinx.android.synthetic.main.item_repository.view.*

class RepoAdapter(var context: Context) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    var repositoryList: MutableList<GitRepository> = mutableListOf()
    lateinit var repoClickListener: RepoClickListener

    fun setData(repositories: MutableList<GitRepository>) {
        repositoryList = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false))
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ownerName.text = repositoryList[position].owner.login
        holder.repositoryName.text = repositoryList[position].name
        holder.watchers.text = repositoryList[position].watchersCount.toString()
        holder.forks.text = repositoryList[position].forksCount.toString()
        holder.issues.text = repositoryList[position].issuesCount.toString()
        holder.stars.text = repositoryList[position].stars.toString()
        holder.updateDate.text = repositoryList[position].updateDate?.format()
        Glide.with(context).load(repositoryList[position].owner.avatarUrl).into(holder.ownerImage)

        holder.ownerImage.setOnClickListener {
            repoClickListener.ownerImageClicked(repositoryList[position].owner.login!!)
        }

        holder.rootLayout.setOnClickListener {
            repoClickListener.repoClicked(repositoryList[position])
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ownerName: TextView = view.ownerName
        var ownerImage: ImageView = view.ownerImage
        var repositoryName: TextView = view.repositoryName
        var watchers: TextView = view.watchers
        var forks: TextView = view.forks
        var issues: TextView = view.issues
        var stars: TextView = view.stars
        var updateDate: TextView = view.updateDate
        var rootLayout: ConstraintLayout = view.rootLayout
    }
}