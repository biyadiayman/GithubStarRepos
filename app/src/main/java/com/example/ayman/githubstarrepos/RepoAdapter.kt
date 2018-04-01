package com.example.ayman.githubstarrepos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_row.view.*

class RepoAdapter(val repoFeed: RepoFeed):RecyclerView.Adapter<RepoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.repo_row, parent, false)
        return RepoViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        //placing each propriety in it's place on the ui
        val item = repoFeed.items.get(position)

        holder?.view?.textView_repo_name?.text = item.name
        holder?.view?.textView_repo_description?.text = item.description
        holder?.view?.textView_owner_login?.text = item.owner.login

        val ownerImageView = holder?.view?.imageView_owner_avatar
        Picasso.with(holder?.view?.context).load(item.owner.avatar_url).into(ownerImageView)


        holder?.view?.textView_rating?.text = coolFormat(item.stargazers_count.toDouble(), 0)

    }

    override fun getItemCount(): Int {
        return repoFeed.items.count()
    }

    //converting 1000 to 1k
    private fun coolFormat(n:Double, iteration:Int):String {
        val c = charArrayOf('k', 'm', 'b', 't')
        val d = n.toLong() / 100 / 10.0
        val isRound = d * 10 % 10 == 0.0//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (if (d < 1000)
        //this determines the class, i.e. 'k', 'm' etc
            ((if (d > 99.9 || isRound || (!isRound && d > 9.99))
            //this decides whether to trim the decimals
                d.toInt() * 10 / 10
            else
                (d).toString() + "" // (int) d * 10 / 10 drops the decimal
                    )).toString() + "" + c[iteration]
        else
            coolFormat(d, iteration + 1))

    }
}

class RepoViewHolder(val view: View): RecyclerView.ViewHolder(view){}