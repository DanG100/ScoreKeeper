package xyz.dgrau.scorekeeper;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ValueEventListener, AdapterView.OnItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public static final int WIFFLE_HIGH = 10;
    public static final int WIFFLE_LOW = 2;
    public static final int SOCCER = 50;
    public static final int FOUL = 25;
    public static final int TECH_FOUL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("1"); // todo pick match
        reference.addValueEventListener(this);
        findViewById(R.id.redHighBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("red","high");
                return true;
            }
        });
        findViewById(R.id.redLowBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("red","low");
                return true;
            }
        });
        findViewById(R.id.redSoccerBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("red","soccer");
                return true;
            }
        });
        findViewById(R.id.redFoulBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("red","foul");
                return true;
            }
        });
        findViewById(R.id.redTechFoulBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("red","techfoul");
                return true;
            }
        });

        findViewById(R.id.blueHighBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("blue","high");
                return true;
            }
        });
        findViewById(R.id.blueLowBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("blue","low");
                return true;
            }
        });
        findViewById(R.id.blueSoccerBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("blue","soccer");
                return true;
            }
        });
        findViewById(R.id.blueFoulBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("blue","foul");
                return true;
            }
        });
        findViewById(R.id.blueTechFoulBut).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                decrementVal("blue","techfoul");
                return true;
            }
        });
        Integer [] array = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(array));
        Spinner spinner = findViewById(R.id.matchSelector);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initDb()
    {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("picked",""+i);
        if(reference != null) {
            Log.d("picked","changing referece");
            reference.removeEventListener(this);
            reference = database.getReference(String.valueOf(i + 1)); // todo pick match
            reference.addValueEventListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void incrementVal(String team, final String key)
    {
        final DatabaseReference ref = reference.child(team);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object count;
                if( (count = dataSnapshot.child(key).getValue()) == null)
                    ref.child(key).setValue(1);
                else {
                    long c = (long) count;
                    ref.child(key).setValue( c + 1);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });
    }
    public void decrementVal(String team, final String key)
    {
        final DatabaseReference ref = reference.child(team);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object count;
                if( (count = dataSnapshot.child(key).getValue()) == null)
                    ref.child(key).setValue(1);
                else {
                    long c = (long) count;
                    ref.child(key).setValue( c - 1);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });
    }


    public void onRedHighClick(View view) {
        incrementVal("red","high");
    }
    public void onRedLowClick(View view) {
        incrementVal("red","low");
    }

    public void onRedSoccerClick(View view) {
        incrementVal("red","soccer");
    }

    public void onRedFoulClick(View view) {
        incrementVal("red","foul");
    }
    public void onRedTechFoulClick(View view) {
        incrementVal("red","techfoul");
    }
    public void onBlueHighClick(View view) {
        incrementVal("blue","high");
    }
    public void onBlueLowClick(View view) {
        incrementVal("blue","low");
    }

    public void onBlueSoccerClick(View view) {
        incrementVal("blue","soccer");
    }

    public void onBlueFoulClick(View view) {
        incrementVal("blue","foul");
    }
    public void onBlueTechFoulClick(View view) {
        incrementVal("blue","techfoul");
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        DataSnapshot redTeam = dataSnapshot.child("red");
        DataSnapshot blueTeam = dataSnapshot.child("blue");

        Object count;
        long redHigh,redLow,redSoccer,redFoul,redTechFoul;
        long blueHigh,blueLow,blueSoccer,blueFoul,blueTechFoul;

        TextView view = findViewById(R.id.redHighText);
        if ((count = redTeam.child("high").getValue()) == null)
            redHigh = 0;
        else {
            redHigh = (long) count;
        }
        view.setText(String.valueOf(redHigh));

        view = findViewById(R.id.redLowText);
        if ((count = redTeam.child("low").getValue()) == null)
            redLow = 0;
        else {
            redLow = (long) count;
        }
        view.setText(String.valueOf(redLow));

        view = findViewById(R.id.redSoccerText);
        if ((count = redTeam.child("soccer").getValue()) == null)
            redSoccer = 0;
        else {
            redSoccer = (long) count;
        }
        view.setText(String.valueOf(redSoccer));

        view = findViewById(R.id.redFoulText);
        if ((count = redTeam.child("foul").getValue()) == null)
            redFoul = 0;
        else {
            redFoul = (long) count;
        }
        view.setText(String.valueOf(redFoul));

        view = findViewById(R.id.redTechFoulText);
        if ((count = redTeam.child("techfoul").getValue()) == null)
            redTechFoul = 0;
        else {
            redTechFoul = (long) count;
        }
        view.setText(String.valueOf(redTechFoul));



        view = findViewById(R.id.blueHighText);
        if ((count = blueTeam.child("high").getValue()) == null)
            blueHigh = 0;
        else {
            blueHigh = (long) count;
        }
        view.setText(String.valueOf(blueHigh));

        view = findViewById(R.id.blueLowText);
        if ((count = blueTeam.child("low").getValue()) == null)
            blueLow = 0;
        else {
            blueLow = (long) count;
        }
        view.setText(String.valueOf(blueLow));

        view = findViewById(R.id.blueSoccerText);
        if ((count = blueTeam.child("soccer").getValue()) == null)
            blueSoccer = 0;
        else {
            blueSoccer = (long) count;
        }
        view.setText(String.valueOf(blueSoccer));

        view = findViewById(R.id.blueFoulText);
        if ((count = blueTeam.child("foul").getValue()) == null)
            blueFoul = 0;
        else {
            blueFoul = (long) count;
        }
        view.setText(String.valueOf(blueFoul));

        view = findViewById(R.id.blueTechFoulText);
        if ((count = blueTeam.child("techfoul").getValue()) == null)
            blueTechFoul = 0;
        else {
            blueTechFoul = (long) count;
        }
        view.setText(String.valueOf(blueTechFoul));

        long redTotal = redHigh * WIFFLE_HIGH + redLow * WIFFLE_LOW + redSoccer * SOCCER + blueFoul * FOUL + blueTechFoul * TECH_FOUL;
        long blueTotal = blueHigh * WIFFLE_HIGH + blueLow * WIFFLE_LOW + blueSoccer * SOCCER + redFoul * FOUL + redTechFoul * TECH_FOUL;

        TextView redTotalView = findViewById(R.id.redTotalView);
        redTotalView.setText(String.valueOf(redTotal));
        TextView blueTotalView = findViewById(R.id.blueTotalView);
        blueTotalView.setText(String.valueOf(blueTotal));


    }

    public void clearMatch(View v)
    {
        DatabaseReference redTeam = reference.child("red");
        DatabaseReference blueTeam = reference.child("blue");
        redTeam.child("high").setValue(0);
        redTeam.child("low").setValue(0);
        redTeam.child("soccer").setValue(0);
        redTeam.child("foul").setValue(0);
        redTeam.child("techfoul").setValue(0);

        blueTeam.child("high").setValue(0);
        blueTeam.child("low").setValue(0);
        blueTeam.child("soccer").setValue(0);
        blueTeam.child("foul").setValue(0);
        blueTeam.child("techfoul").setValue(0);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
