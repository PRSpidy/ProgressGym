import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.progressgym.R
import com.example.progressgym.data.model.Set
import com.example.progressgym.data.model.TablaItem
import com.example.progressgym.data.model.Training
import kotlin.reflect.KFunction1

class SetAdapter(
    private var tablaItemList: List<TablaItem>,
    private val insertSetRoom: (Set) -> Int,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_NORMAL = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_table, parent, false)
            TablaViewHolder(view)
        }
    }

    fun updateItemId(id: Int, position: Int) {
        if (position in 0 until tablaItemList.size) {
            tablaItemList[position].id = id
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            val tablaViewHolder = holder as TablaViewHolder
            val item = tablaItemList[position - 1]
            Log.i(position.toString(), item.set.toString())

            if(item.set == 0){
                item.set = position
            }

            Log.i("id", item.id.toString())
            tablaViewHolder.colum1.text = tablaViewHolder.itemView.context.getString(R.string.set) + " " + item.set
            tablaViewHolder.colum2.text = item.repsObj
            tablaViewHolder.colum3.text = item.weightObj
            tablaViewHolder.colum4.text = item.reps
            tablaViewHolder.colum5.text = item.weight
            tablaViewHolder.colum6.text = item.set.toString()
            tablaViewHolder.colum7.text = item.id.toString()

            if (position % 2 == 0) {
                tablaViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(tablaViewHolder.itemView.context, R.color.grey))
            } else {
                tablaViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(tablaViewHolder.itemView.context, R.color.white))
            }

            tablaViewHolder.colum2.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkAllFieldsModified(tablaViewHolder)
                }
            }
            tablaViewHolder.colum3.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkAllFieldsModified(tablaViewHolder)
                }
            }
            tablaViewHolder.colum4.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkAllFieldsModified(tablaViewHolder)
                }
            }
            tablaViewHolder.colum5.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    checkAllFieldsModified(tablaViewHolder)
                }
            }
        }
    }

    private fun checkAllFieldsModified(holder: TablaViewHolder) {
        val repsObj = holder.colum2.text.toString()
        val pesoObj = holder.colum3.text.toString()
        val reps = holder.colum4.text.toString()
        val peso = holder.colum5.text.toString()
        val setNumber = holder.colum6.text.toString()
        val setId = holder.colum7.text.toString()
        Log.i("position", "position.toString()")
        val position = holder.adapterPosition
        Log.i("position", position.toString())
        if (repsObj.isNotEmpty() && pesoObj.isNotEmpty() && reps.isNotEmpty() && peso.isNotEmpty()) {
            val set = Set(setId.toInt(), setNumber.toInt(), repsObj.toInt(), pesoObj.toFloat(), reps.toInt(), peso.toFloat())
            if(position > 0){
                val item1 = tablaItemList[position - 1]
                val item = Set(item1.id.toInt(), item1.set.toInt(), item1.repsObj.toInt(), item1.weightObj.toFloat(), item1.reps.toInt(), item1.weight.toFloat())
                if(set != item){
                    insertSetRoom(set)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tablaItemList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class TablaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colum1: TextView = itemView.findViewById(R.id.text_serie)
        val colum2: TextView = itemView.findViewById(R.id.edit_reps_obj)
        val colum3: TextView = itemView.findViewById(R.id.edit_peso_obj)
        val colum4: TextView = itemView.findViewById(R.id.edit_reps)
        val colum5: TextView = itemView.findViewById(R.id.edit_peso)
        val colum6: TextView = itemView.findViewById(R.id.set_number)
        val colum7: TextView = itemView.findViewById(R.id.set_id)
    }
}
