package com.satunol.satunolcodetest1;

import android.content.Context;
import android.graphics.Insets;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.carousel.MaskableFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.satunol.satunolcodetest1.model.PointModel;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    PointModel pointModel;
    CanvasView canvasView;
    List<PointModel> pointModelArrayList;
    MaterialButton mButton;
    TextInputEditText xInput, yInput, sideLengthInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xInput = findViewById(R.id.input_x);
        yInput = findViewById(R.id.input_y);
        sideLengthInput = findViewById(R.id.input_sd);
        mButton = findViewById(R.id.materialButton);

        resetData(this);
        generateAndStore(getApplicationContext());
        retrieveData();

        mButton.setOnClickListener(v -> buttonHandler());

    }

    public int getScreenWidth() {
        WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets()
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        return windowMetrics.getBounds().width() - insets.left - insets.right;
    }

    public int getScreenHeight() {
        WindowMetrics windowMetrics = this.getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets()
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        return windowMetrics.getBounds().height() - insets.top - insets.bottom;
    }

    public void generateAndStore(Context context) {
        databaseHelper = new DatabaseHelper(context);

        for (int i = 1; i <= 500; i++) {
            // Generate x and y values as needed
            Random randomCoordinate = new Random();
            float x = 10 + randomCoordinate.nextFloat() * (getScreenWidth() - 10);
            float y = 10 + randomCoordinate.nextFloat() * (getScreenHeight() - 200);
            pointModel = new PointModel(i, x, y);
            databaseHelper.generateRandomPoints(pointModel);
        }
    }

    public void resetData(Context context) {
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.resetData();
    }

    public void retrieveData() {
        DatabaseHelper db = new DatabaseHelper(this);
        pointModelArrayList = db.getAllPoints();
    }

    public void callView() {
        int x = Integer.parseInt(Objects.requireNonNull(xInput.getText()).toString());
        int y = Integer.parseInt(Objects.requireNonNull(yInput.getText()).toString());
        int sd = Integer.parseInt(Objects.requireNonNull(sideLengthInput.getText()).toString());
//        for (PointModel point : pointModelArrayList) {
//            Log.d("valerie", point.getId() + " | " + point.getX() + " | " + point.getY());
//        }

        //initializing our view
        MaskableFrameLayout maskableFrameLayout = findViewById(R.id.frameLayout);

        // calling our canvas view class and adding its view to our relative layout.
        canvasView = new CanvasView(this);
        canvasView.setPointList(pointModelArrayList);
        canvasView.setRectPosition(x, y, sd);

        // Set data to the canvas view
        maskableFrameLayout.addView(canvasView);
    }

    public void buttonHandler() {

        if (areFieldsFilled()) {
            callView();
            Toast.makeText(this, "Selection complete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areFieldsFilled() {
        return
            !Objects.requireNonNull(xInput.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(yInput.getText()).toString().isEmpty() &&
            !Objects.requireNonNull(sideLengthInput.getText()).toString().isEmpty();
    }

}