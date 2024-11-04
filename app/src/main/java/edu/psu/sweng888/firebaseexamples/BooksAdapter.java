package edu.psu.sweng888.firebaseexamples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Books> booksList;
    private DatabaseReference booksRef;

    public BooksAdapter(List<Books> booksList, DatabaseReference booksRef) {
        this.booksList = booksList;
        this.booksRef = booksRef;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books book = booksList.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.isbnTextView.setText(book.getIsbn() != null ? String.valueOf(book.getIsbn()) : "N/A");
        holder.publicationDateTextView.setText(book.getPublicationDate() != null ? book.getPublicationDate() : "N/A");

        // Set up delete button
        holder.deleteButton.setOnClickListener(v -> {
            String isbnKey = String.valueOf(book.getIsbn()); // Use ISBN as key for deletion
            if (isbnKey != null) {
                // Remove item from Firebase
                booksRef.child(isbnKey).removeValue().addOnSuccessListener(aVoid -> {
                    // Remove item from list and notify adapter
                    booksList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, booksList.size());
                    Toast.makeText(holder.itemView.getContext(), "Book deleted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(holder.itemView.getContext(), "Failed to delete book", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView, isbnTextView, publicationDateTextView;
        Button deleteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            authorTextView = itemView.findViewById(R.id.author_text_view);
            isbnTextView = itemView.findViewById(R.id.isbn_text_view);
            publicationDateTextView = itemView.findViewById(R.id.publication_date_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}

