package com.example.hyperlocalmarket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyperlocalmarket.adapter.ProductImageAdapter;
import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.AttributeOption;
import com.example.hyperlocalmarket.model.Category;
import com.example.hyperlocalmarket.model.CategoryAttribute;
import com.example.hyperlocalmarket.model.ProductAttributeRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {

    Spinner categorySpinner,spinnerCondition,spinnerListingStatus;
    private List<Category> categories;
    Long categoryId;
    String categoryName;

    private Button btnSelectImage;
    private final List<Uri> selectedImages=new ArrayList<>();
    RecyclerView recyclerProductImages;

    private ProductImageAdapter imageAdapter;
    private LinearLayout layoutAttributes;

    EditText etItemName;
    EditText etDescription;
    EditText etPrice;


    private List<CategoryAttribute> categoryAttributes =
            new ArrayList<>();

    private final List<ProductAttributeRequest> attributeRequests =
            new ArrayList<>();

    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetMultipleContents(),
                    uris -> {

                        if (uris == null || uris.isEmpty()) {
                            return;
                        }

                        // Maximum 5 images
                        if (uris.size() > 5) {

                            Toast.makeText(
                                    requireContext(),
                                    "You can select maximum 5 images",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        selectedImages.clear();
                        selectedImages.addAll(uris);

                        imageAdapter.notifyDataSetChanged();

                        Log.d(
                                "IMAGES",
                                "Selected: " + selectedImages.size()
                        );
                    }
            );



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_item, container, false);

        categorySpinner=view.findViewById(R.id.spinnerCategory);
        spinnerCondition=view.findViewById(R.id.spinnerCondition);

        recyclerProductImages=view.findViewById(R.id.recyclerProductImages);

        imageAdapter = new ProductImageAdapter(selectedImages);
        layoutAttributes = view.findViewById(R.id.layoutAttributes);

        etItemName = view.findViewById(R.id.etItemName);
        etDescription = view.findViewById(R.id.etDescription);
        etPrice = view.findViewById(R.id.etPrice);

        recyclerProductImages.setLayoutManager(
                new LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        recyclerProductImages.setAdapter(imageAdapter);

        List<String> productCondition= Arrays.asList("NEW",
                "LIKE NEW",
                "GOOD",
                "FAIR",
                "POOR");

        List<String> listingStatus=Arrays.asList("ACTIVE",
                "SOLD",
                "EXPIRED",
                "CANCELLED");

        //Product Condition
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(requireContext(),R.layout.spinner_list,productCondition);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list);
        spinnerCondition.setAdapter(arrayAdapter);



        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectItem=productCondition.get(i);
                Log.d(
                        "CONDITION",
                        "Selected: " + selectItem
                );

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        SharedPreferences preferences=requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token =preferences.getString("jwt",null);
        ApiService apiService= RetrofitClient.getApiService();

        apiService.getCategory("Bearer "+token).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("CATEGORY_API", "Response code: " + response.code());
                if (response.isSuccessful()){
                    categories=response.body();

                    List<String> categoryNames=new ArrayList<>();


                    for(Category category1:categories){
                        categoryNames.add(category1.getName());
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(requireContext(), R.layout.spinner_list,categoryNames);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list);

                    categorySpinner.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("API", t.getMessage());

            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (categories != null && !categories.isEmpty()){
                    Category category=categories.get(i);
                    categoryId=category.getId();
                    categoryName=category.getName();
                    loadCategoryAttributes(categoryId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        btnSelectImage = view.findViewById(R.id.btnSelectImages);

        btnSelectImage.setOnClickListener(v -> {

            imagePicker.launch("image/*");

        });
        Button btnPostItem = view.findViewById(R.id.btnPostItem);

        btnPostItem.setOnClickListener(v -> {

//            createListing();

        });


        return view;

    }
    private void showAttributes(List<CategoryAttribute> attributes) {

        layoutAttributes.removeAllViews();

        categoryAttributes.clear();
        categoryAttributes.addAll(attributes);

        for (CategoryAttribute attribute : attributes) {

            if (!attribute.isActive()) {
                continue;
            }

            addAttributeView(attribute);
        }
    }
    private void addAttributeView(CategoryAttribute attribute) {

        TextView label = new TextView(requireContext());

        String labelText = attribute.getName();

        if (attribute.isRequired()) {
            labelText += " *";
        }

        label.setText(labelText);
        label.setTextColor(
                getResources().getColor(
                        R.color.text_primary
                )
        );

        label.setTextSize(15);
        label.setPadding(0, dp(16), 0, dp(8));

        layoutAttributes.addView(label);

        String type = attribute.getDataType();

        switch (type) {

            case "TEXT":
                addTextAttribute(attribute);
                break;

            case "NUMBER":
                addNumberAttribute(attribute);
                break;

            case "BOOLEAN":
                addBooleanAttribute(attribute);
                break;

            case "SELECT":
                addSelectAttribute(attribute);
                break;

            case "MULTI_SELECT":
                addMultiSelectAttribute(attribute);
                break;

            case "DATE":
                addDateAttribute(attribute);
                break;
        }
    }
    private void addTextAttribute(CategoryAttribute attribute) {

        EditText editText = new EditText(requireContext());

        editText.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(60)
                )
        );

        editText.setHint("Enter " + attribute.getName());
        editText.setBackgroundResource(R.drawable.dev_box);
        editText.setPadding(dp(12), 0, dp(12), 0);


        layoutAttributes.addView(editText);

        editText.setTag(attribute.getId());
    }
    private void addNumberAttribute(CategoryAttribute attribute) {

        EditText editText = new EditText(requireContext());

        editText.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER |
                        android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        editText.setHint(
                attribute.getUnit() != null
                        ? "Enter " + attribute.getName()
                        : "Enter value"
        );

        editText.setBackgroundResource(R.drawable.dev_box);
        editText.setPadding(12, 0, 12, 0);

        editText.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(52)
                )
        );

        layoutAttributes.addView(editText);

        editText.setTag(attribute.getId());
    }
    private void addBooleanAttribute(CategoryAttribute attribute) {

        Switch switchView = new Switch(requireContext());

        switchView.setText(attribute.getName());
        switchView.setTextColor(
                getResources().getColor(
                        R.color.text_primary
                )
        );

        switchView.setTag(attribute.getId());

        layoutAttributes.addView(switchView);
    }
    private void addSelectAttribute(CategoryAttribute attribute) {

        Spinner spinner = new Spinner(requireContext());

        spinner.setBackgroundResource(R.drawable.dev_box);

        List<AttributeOption> options = attribute.getOptions();

        List<String> names = new ArrayList<>();

        for (AttributeOption option : options) {
            names.add(
                    option.getDisplayName() != null
                            ? option.getDisplayName()
                            : option.getValue()
            );
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        R.layout.spinner_list,
                        names
                );

        adapter.setDropDownViewResource(
                R.layout.spinner_dropdown_list
        );

        spinner.setAdapter(adapter);

        spinner.setTag(attribute);

        layoutAttributes.addView(spinner);
    }
    private void addMultiSelectAttribute(
            CategoryAttribute attribute) {

        LinearLayout container =
                new LinearLayout(requireContext());

        container.setOrientation(
                LinearLayout.VERTICAL
        );

        for (AttributeOption option :
                attribute.getOptions()) {

            CheckBox checkBox =
                    new CheckBox(requireContext());

            checkBox.setText(
                    option.getDisplayName() != null
                            ? option.getDisplayName()
                            : option.getValue()
            );

            checkBox.setTextColor(
                    getResources().getColor(
                            R.color.text_primary
                    )
            );

            checkBox.setTag(option.getId());

            container.addView(checkBox);
        }

        container.setTag(attribute.getId());

        layoutAttributes.addView(container);
    }
    private void addDateAttribute(
            CategoryAttribute attribute) {

        Button button = new Button(requireContext());

        button.setText("Select " + attribute.getName());

        button.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            requireContext(),
                            (datePicker, year, month, day) -> {

                                String date =
                                        String.format(
                                                "%04d-%02d-%02d",
                                                year,
                                                month + 1,
                                                day
                                        );

                                button.setText(date);

                                button.setTag(date);
                            },
                            calendar.get(
                                    Calendar.YEAR
                            ),
                            calendar.get(
                                    Calendar.MONTH
                            ),
                            calendar.get(
                                    Calendar.DAY_OF_MONTH
                            )
                    );

            dialog.show();
        });

        button.setTag(attribute.getId());

        layoutAttributes.addView(button);
    }

    private void loadCategoryAttributes(Long categoryId) {

        SharedPreferences preferences =
                requireContext().getSharedPreferences(
                        "auth",
                        Context.MODE_PRIVATE
                );

        String token =
                preferences.getString("jwt", null);

        if (token == null) {
            Log.e("ATTRIBUTE_API", "JWT token not found");
            return;
        }

        ApiService apiService =
                RetrofitClient.getApiService();

        apiService.getCategoryAttributes(
                categoryId,
                "Bearer " + token
        ).enqueue(new Callback<List<CategoryAttribute>>() {

            @Override
            public void onResponse(
                    Call<List<CategoryAttribute>> call,
                    Response<List<CategoryAttribute>> response) {

                Log.d(
                        "ATTRIBUTE_API",
                        "Response code: " + response.code()
                );

                if (response.isSuccessful()
                        && response.body() != null) {

                    showAttributes(response.body());

                } else {

                    Log.e(
                            "ATTRIBUTE_API",
                            "Failed: " + response.code()
                    );

                    layoutAttributes.removeAllViews();
                }
            }

            @Override
            public void onFailure(
                    Call<List<CategoryAttribute>> call,
                    Throwable t) {

                Log.e(
                        "ATTRIBUTE_API",
                        "API failed",
                        t
                );
            }
        });
    }
    private int dp(int value) {
        return (int) (
                value * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
//    private void createListing() {
//
//        if (categoryId == null) {
//            Toast.makeText(
//                    requireContext(),
//                    "Please select a category",
//                    Toast.LENGTH_SHORT
//            ).show();
//            return;
//        }
//
//        String title =
//                etItemName.getText()
//                        .toString()
//                        .trim();
//
//        String description =
//                etDescription.getText()
//                        .toString()
//                        .trim();
//
//        String priceText =
//                etPrice.getText()
//                        .toString()
//                        .trim();
//
//        if (title.isEmpty()) {
//            etItemName.setError(
//                    "Enter item name"
//            );
//            return;
//        }
//
//        if (priceText.isEmpty()) {
//            etPrice.setError(
//                    "Enter price"
//            );
//            return;
//        }
//
//        BigDecimal price =
//                new BigDecimal(priceText);
//
//        String condition =
//                spinnerCondition
//                        .getSelectedItem()
//                        .toString();
//
//        List<ProductAttributeRequest> attributes =
//                buildProductAttributeRequests();
//
//        CreateListingRequest request =
//                new CreateListingRequest();
//
//        request.setCategoryId(categoryId);
//        request.setTitle(title);
//        request.setDescription(description);
//        request.setPrice(price);
//        request.setCondition(condition);
//        request.setListingType("SELL");
//        request.setAttributes(attributes);
//
//        // imageUrls will be added after image upload
//
//        sendListingToBackend(request);
//    }

}