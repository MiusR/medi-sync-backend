package com.mihair.analysis_machine.model.patients;

public enum PatientStates {
    OK,         // Normal return code
    CRITICAL,   // About to go into heart failure
    MONITORING,  // Intermediate state in between CRITICAL and OK - getting close to limits
    DISCONNECTED, // NO data received since last check
    NO_STATE      // NO registered patient state, used for errors
}
