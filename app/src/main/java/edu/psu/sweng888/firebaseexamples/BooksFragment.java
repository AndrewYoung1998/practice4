package edu.psu.sweng888.firebaseexamples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    private BooksAdapter adapter;
    private List<Books> booksList;
    private DatabaseReference booksRef;

    private EditText titleInput, authorInput, isbnInput, publicationDateInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Database reference
        booksRef = FirebaseDatabase.getInstance().getReference("books");

        // Initialize UI elements
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        titleInput = view.findViewById(R.id.title_input);
        authorInput = view.findViewById(R.id.author_input);
        isbnInput = view.findViewById(R.id.isbn_input);
        publicationDateInput = view.findViewById(R.id.publication_date_input);
        Button addButton = view.findViewById(R.id.add_book_button);

        // Initialize book list and adapter
        booksList = new ArrayList<>();
        adapter = new BooksAdapter(booksList, booksRef);
        recyclerView.setAdapter(adapter);

        // Load books from Firebase
        loadBooks();

        // Set up add book button functionality
        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String author = authorInput.getText().toString().trim();
            String isbnString = isbnInput.getText().toString().trim();
            String publicationDate = publicationDateInput.getText().toString().trim();

            if (!title.isEmpty() && !author.isEmpty() && !isbnString.isEmpty() && !publicationDate.isEmpty()) {
                Long isbn = Long.parseLong(isbnString); // Parse ISBN to Long
                Books newBook = new Books(author, isbn, publicationDate, null, title); // assuming publisher is not required

                // Use ISBN as the unique key in Firebase
                booksRef.child(isbnString).setValue(newBook).addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Book added", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    titleInput.setText("");
                    authorInput.setText("");
                    isbnInput.setText("");
                    publicationDateInput.setText("");
                }).addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to add book", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBooks() {
        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booksList.clear(); // Clear the list to avoid duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Books book = dataSnapshot.getValue(Books.class);
                    if (book != null) {
                        booksList.add(book); // Add each book to the list
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load books", Toast.LENGTH_SHORT).show();
            }
        });
    }

}