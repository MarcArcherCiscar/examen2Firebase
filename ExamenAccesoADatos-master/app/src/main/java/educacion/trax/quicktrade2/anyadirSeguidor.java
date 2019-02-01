package educacion.trax.quicktrade2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class anyadirSeguidor extends AppCompatActivity implements View.OnClickListener {

    private Persona p;
    private SeguirUsuario seg;
    private DatabaseReference bbdd, bbdd2;
    private EditText nombreUsu;
    private TextView result;
    private Button buscar;
    private Button aceptar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_seguidor);

        nombreUsu = findViewById(R.id.etSeguir);
        result = findViewById(R.id.resultadoSeguir);
        buscar = findViewById(R.id.comprobarUsu);
        buscar.setOnClickListener(this);
        aceptar = findViewById(R.id.agregarUsu);
        aceptar.setOnClickListener(this);
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.comprobarUsu) {

            String seguidor = String.valueOf(nombreUsu.getText());
            seg = new SeguirUsuario(seguidor);
            Query q = bbdd.orderByChild("id").equalTo(seguidor);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        result.setText("");
                        SeguirUsuario seguirUsuario = ds.getValue(SeguirUsuario.class);
                        String name = seguirUsuario.getNombre();
                        result.setText(name);
                        if (result.getText().equals("")) {
                            Toast.makeText(getApplicationContext(), "No se ha trobat res", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if (view.getId() == R.id.agregarUsu) {
            String seguidor2 = String.valueOf(nombreUsu.getText());
            seg = new SeguirUsuario(seguidor2);
            if (!result.getText().equals("")) {
                Query q = bbdd.orderByChild("id").equalTo(seguidor2);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            final String key = ds.getKey();
                            bbdd.child(ds.getKey()).child("nom").child(key).setValue(seg);
                            Toast.makeText(getApplicationContext(),"Añadit correctament",Toast.LENGTH_LONG).show();
                            if (result.getText().equals("")) {
                                Toast.makeText(getApplicationContext(),"No se ha trobat res.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}