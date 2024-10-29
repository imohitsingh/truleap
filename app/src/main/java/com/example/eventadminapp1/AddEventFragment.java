package com.example.eventadminapp1;
import android.Manifest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddEventFragment extends Fragment {

    private EditText etEventName, etLocation, etPrice, etTickets;
    private ImageView ivEventImage;
    private Button btnSelectImage, btnSaveEvent;
    private Uri imageUri;
    private FirebaseFirestore db;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;

    public AddEventFragment() {
        // Required empty public constructor
    }

    public static AddEventFragment newInstance() {
        return new AddEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        // Initialize views
        etEventName = view.findViewById(R.id.etEventName);
        etLocation = view.findViewById(R.id.etLocation);
        etPrice = view.findViewById(R.id.etPrice);
        etTickets = view.findViewById(R.id.etTickets);
        ivEventImage = view.findViewById(R.id.ivEventImage);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        btnSaveEvent = view.findViewById(R.id.btnSaveEvent);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("event_images");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving event...");

        // Set listeners
//        btnSelectImage.setOnClickListener(v -> {
//            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                openFileChooser();
//            } else {
//                PermissionUtils.requestStoragePermission(getActivity());
//            }
//        });

       // btnSelectImage.setOnClickListener(v -> openFileChooser());
//        btnSelectImage.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Select Image button clicked if", Toast.LENGTH_SHORT).show();
//
//            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                openFileChooser(); // Open file chooser directly if permission is granted
//            } else {
//                Toast.makeText(getContext(), "Select Image button clicked else", Toast.LENGTH_SHORT).show();
//
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionUtils.STORAGE_PERMISSION_CODE);
//            }
//        });

//        btnSelectImage.setOnClickListener(v -> {
//            // Log and Toast the current permission state for clarity
//            boolean hasPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//            Toast.makeText(getContext(), "Permission status: " + hasPermission, Toast.LENGTH_SHORT).show();
//
//            if (hasPermission) {
//                openFileChooser(); // Directly open file picker if permission is granted
//            } else {
//                Toast.makeText(getContext(), "Requesting Permission", Toast.LENGTH_SHORT).show();
//                // Request permission directly from the fragment
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionUtils.STORAGE_PERMISSION_CODE);
//            }
//        });
//        btnSelectImage.setOnClickListener(v -> {
//            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                // Request permission directly
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
//            } else {
//                openFileChooser(); // Open file picker if permission is granted
//            }
//        });

        btnSelectImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openFileChooser();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Request permission normally
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            } else {
                // Permission has been denied permanently
                Toast.makeText(getContext(), "Permission denied permanently. Please enable it in settings.", Toast.LENGTH_LONG).show();
                // Direct the user to app settings
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", requireContext().getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });




        btnSaveEvent.setOnClickListener(v -> saveEvent());

        return view;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        Log.d("AddEventFragment", "onRequestPermissionsResult called");  // Add this line
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (PermissionUtils.handlePermissionResult(getContext(), requestCode, grantResults)) {
//
//            Log.d("AddEventFragment", "Permission granted, opening file chooser");
//
//            // Permission granted, proceed with opening the file chooser if select button was clicked
//            openFileChooser();
//        }
//        else {
//            Log.d("AddEventFragment", "Permission denied");
//        }
//    }

//

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Toast.makeText(getContext(), "onRequestPermissionsResult triggered", Toast.LENGTH_SHORT).show();
//        if (requestCode == PermissionUtils.STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openFileChooser();
//            } else {
//                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == 101) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFileChooser();
        } else {
            Toast.makeText(getContext(), "Permission denied. Please enable it in settings.", Toast.LENGTH_LONG).show();
        }
    }
}





    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(ivEventImage);
        }
    }

    private void saveEvent() {



        String eventName = etEventName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String ticketsStr = etTickets.getText().toString().trim();

        if (eventName.isEmpty() || location.isEmpty() || priceStr.isEmpty() || ticketsStr.isEmpty() || imageUri == null) {
            Toast.makeText(getContext(), "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }


        double price = Double.parseDouble(priceStr);
        int ticketsAvailable = Integer.parseInt(ticketsStr);

        Long eventId = System.currentTimeMillis();  // Generate a unique Long ID based on current time

        progressDialog.show();

        // Upload image to Firebase Storage
        StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        fileReference.putFile(imageUri)
                .continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                })
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String imageUrl = downloadUri.toString();

                        // Save event details to Firestore
                        Event event = new Event(eventId, eventName, location, price, ticketsAvailable, imageUrl);

                        String string_eventid=String.valueOf(eventId);
                        db.collection("events").document(string_eventid)
                                .set(event)
                                .addOnSuccessListener(aVoid -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Event added successfully.", Toast.LENGTH_SHORT).show();
                                    clearFields();
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Error saving event.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error uploading image.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = requireContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void clearFields() {
        etEventName.setText("");
        etLocation.setText("");
        etPrice.setText("");
        etTickets.setText("");
        ivEventImage.setImageResource(0);
        imageUri = null;
    }
}
