package com.stuypulse.stuylib.network;

import java.util.function.Function;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.Conversion;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;

public class SmartAngle {
    
    private static final int LISTENER_FLAGS = EntryListenerFlags.kUpdate | EntryListenerFlags.kNew;
    
    private static final Conversion<Double, Angle> DEGREES = Conversion.make(Angle::fromDegrees, a->a.toDegrees());
    private static final Conversion<Double, Angle> RADIANS = Conversion.make(Angle::fromRadians, a->a.toRadians());

    private NetworkTableEntry mEntry;
    private double mDefaultValue;

    private Conversion<Double, Angle> mConversion;

    private Angle mAngle;

    public SmartAngle(NetworkTableEntry entry, double value) {
        degrees();

        mEntry = entry;
        mDefaultValue = value;
        mEntry.setDefaultValue(value);
        
        mEntry.addListener(this::update, LISTENER_FLAGS);
    }

    public SmartAngle(NetworkTableEntry entry, Angle value) {
        this(entry, )
    }

    private void update(double value) {
        mAngle = mConversion.to(value);
    }

    private void update(EntryNotification notif) {
        update(notif.value.getDouble());
    }

    public SmartAngle units(Conversion<Double, Angle> conversion) {
        mConversion = conversion;
        return this;
    }

    public SmartAngle radians() {
        return units(RADIANS);
    }

    public SmartAngle degrees() {
        return units(DEGREES);
    }
    
    public Angle getAngle() {
        return mAngle;
    }

    public void set(Angle angle) {
        set(mConversion.from(angle));
    }

    public void set(double angle) {
        mEntry.forceSetDouble(angle);
    }
}
