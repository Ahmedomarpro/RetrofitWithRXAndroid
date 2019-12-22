package com.ao.adapter;

 import android.view.LayoutInflater;
 import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


 import com.ao.retrofitrxjava.databinding.ItemRepoBinding;
 import com.ao.retrofitrxjava.model.ResponseRepos;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

	private static final String TAG = ReposAdapter.class.getSimpleName();

	//private Context context;
	protected List<ResponseRepos> list;
	public OnItemClickListenerView onItemClickListener;


	public ReposAdapter(List<ResponseRepos> list) {
		this.list = list;
	}

	public void setList(List<ResponseRepos> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		/*LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		itemrepo binding = itemrepo.inflate(layoutInflater,
				parent, false);
		return new ViewHolder(binding);*/

		LayoutInflater layoutInflater1 = LayoutInflater.from(parent.getContext());
		ItemRepoBinding  bin = ItemRepoBinding.inflate(layoutInflater1,parent,false);
		return new ViewHolder(bin);
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		ResponseRepos item = list.get(position);

		holder.binding.setVmdata(item);

		//holder.ntName.setText(item.getName());
		//holder.ntDes.setText(item.getDescription());


		holder.binding.getRoot().setOnClickListener(v -> {
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(position, item);
			}
		});
	}


	@Override
	public int getItemCount() {
		//return iterator
		return list == null ? 0 : list.size();

	}

	public void setOnItemClickListener(OnItemClickListenerView onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListenerView {
		void onItemClick(int position, ResponseRepos item);
	}


	/*public static class ViewHolder extends RecyclerView.ViewHolder {
		//ViewHolder
		TextView tvName;
		TextView tvDesc;

		public ViewHolder(View itemView) {
			super(itemView);

				tvName = itemView.findViewById(R.id.tvName);
				tvDesc = itemView.findViewById(R.id.tvDesc);

		}
	}*/
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private TextView ntName, ntDes;
		ItemRepoBinding binding ;

		public ViewHolder(@NonNull ItemRepoBinding binding) {
			super(binding.getRoot());
			this.binding = binding;

			//ntName = itemView.findViewById(R.id.tvName);
			//ntDes = itemView.findViewById(R.id.tvDesc);
		}
	}
}