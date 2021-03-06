package com.rallygeologico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    ImageView fotoPerfil;
    ImageView fotoFondo;
    ImageView editar;
    TextView nombreUsuario;
    TextView lugar;
    TextView recorridos;
    TextView recorridosTotal;
    TextView puntos;
    TextView puntosTotal;
    TextView prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fotoFondo = findViewById(R.id.header_cover_image);
        fotoPerfil = findViewById(R.id.profile);
        editar = findViewById(R.id.edit);
        nombreUsuario = findViewById(R.id.nombre);
        lugar = findViewById(R.id.ubicacion);
        recorridos = findViewById(R.id.rallies);
        puntos = findViewById(R.id.points);
        recorridosTotal = findViewById(R.id.rallyCounter);
        puntosTotal = findViewById(R.id.pointsCounter);
        prueba = findViewById(R.id.prueba);

        boolean conectado = AccessToken.getCurrentAccessToken() != null;

        Profile perfil = Profile.getCurrentProfile();
        if (conectado && perfil != null) {
            new FacebookFragment.LoadProfileImage(fotoPerfil).execute(perfil.getProfilePictureUri(200, 200).toString());
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (object != null) {
                                Log.d("Request", object.toString());
                                String name = JSONParser.getName(object);
                                nombreUsuario.setText(name);
                                String home = JSONParser.getHometown(object);
                                lugar.setText(home);
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,hometown");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

}
