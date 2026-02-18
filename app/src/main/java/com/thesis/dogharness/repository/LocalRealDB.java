package com.thesis.dogharness.repository;

import com.github.MakMoinee.library.models.RealtimeDBBody;
import com.github.MakMoinee.library.services.RealtimeDbRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LocalRealDB extends RealtimeDbRequest {

    DatabaseReference dbRef;
    public LocalRealDB() {
        super();
    }

    public void getAllData(ValueEventListener listener) {
        dbRef = this.getDbRef();
        RealtimeDBBody body = new RealtimeDBBody.RealtimeDbBodyBuilder()
                .setChildName("sensor_data")
                .build();

        dbRef.child(body.getChildName()).orderByKey().addValueEventListener(listener);

    }
}
