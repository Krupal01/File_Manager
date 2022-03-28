package com.example.filemanager.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.R
import com.example.filemanager.ui.MainActivity
import com.example.filemanager.utils.Constants
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File
import java.util.ArrayList
import javax.inject.Inject


class FileListAdapter @Inject constructor(@ActivityContext private val context: Context) : RecyclerView.Adapter<FileListAdapter.FileViewHolder>() {

    private var files = ArrayList<File>()

    fun submitData(files : List<File>){
        this.files = files as ArrayList<File>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_file,parent,false)
        return FileViewHolder(view,context)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class FileViewHolder(view : View , private val context: Context) : RecyclerView.ViewHolder(view) {
        var icon : ImageView = view.findViewById(R.id.imgFileIcon)
        var title : TextView = view.findViewById(R.id.tvFileName)

        fun bind(file: File){
            if (file.isDirectory){
                icon.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_folder_foreground))
            }else{
                icon.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_file_foreground))
            }
            title.text = file.name

            itemView.setOnClickListener {
                if (file.isDirectory){
                    var intent = Intent(context , MainActivity::class.java)
                    intent.putExtra(Constants.FILE_PATH,file.absolutePath.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)

                }else{
                    var intent = Intent()
                    intent.action = android.content.Intent.ACTION_VIEW
                    intent.setDataAndType(Uri.parse(file.absolutePath),"*/*")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
        }

    }

}