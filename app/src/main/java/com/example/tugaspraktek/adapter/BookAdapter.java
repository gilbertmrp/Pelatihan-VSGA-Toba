package com.example.tugaspraktek.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugaspraktek.EditActivity;
import com.example.tugaspraktek.R;
import com.example.tugaspraktek.controller.BookController;
import com.example.tugaspraktek.model.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private int resourceLayout;
    private Context mContext;

    public BookAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Book p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tvTitle);
            TextView tt2 = (TextView) v.findViewById(R.id.tvAuthor);
            TextView tt3 = (TextView) v.findViewById(R.id.tvTahun);
            Button btnEdit = v.findViewById(R.id.btnEdit);
            Button btnDelete = v.findViewById(R.id.btnDelete);

            if (tt1 != null) {
                tt1.setText(p.getTitle());
            }

            if (tt2 != null) {
                tt2.setText(p.getAuthor());
            }

            if (tt3 != null) {
                tt3.setText(String.valueOf(p.getTahun()));
            }

            btnEdit.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("BOOK_ID", p.getId());
                mContext.startActivity(intent);
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Yakin ingin menghapus")
                            .setMessage("Apakah anda yakin ingin menghapus buku ini?")
                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                                BookController bookController = new BookController(mContext);
                                if (bookController.deleteBook(p.getId())) {
                                    Toast.makeText(mContext, "Berhasil menghapus buku", Toast.LENGTH_SHORT).show();
                                    remove(p);  // Hapus item dari list dan refresh adapter
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(mContext, "Gagal menghapus buku", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

        return v;
    }
}
