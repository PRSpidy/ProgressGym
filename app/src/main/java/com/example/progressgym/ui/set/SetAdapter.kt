import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.progressgym.R
import com.example.progressgym.data.model.TablaItem

class SetAdapter(
    private val tablaItemList: List<TablaItem>
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            val tablaViewHolder = holder as TablaViewHolder
            val item = tablaItemList[position - 1] // Restar 1 para ajustar la posición debido al encabezado
            tablaViewHolder.colum1.text = tablaViewHolder.itemView.context.getString(R.string.set) + " " + item.set
            tablaViewHolder.colum2.text = item.repsObj
            tablaViewHolder.colum3.text = item.weightObj
            tablaViewHolder.colum4.text = item.reps
            tablaViewHolder.colum5.text = item.weight

            // Cambiar el color de fondo de la fila según si es par o impar
            if (position % 2 == 0) {
                tablaViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(tablaViewHolder.itemView.context, R.color.grey))
            } else {
                tablaViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(tablaViewHolder.itemView.context, R.color.white))
            }
        }
    }

    override fun getItemCount(): Int {
        // Sumar 1 para incluir la fila de encabezado
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
    }
}
