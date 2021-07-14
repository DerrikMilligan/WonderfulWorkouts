package wonderful.workouts.callbacks;

import wonderful.workouts.database.joiners.MovementWithWorkoutMovementHistory;

public interface ActiveWorkoutAdapterCallback {
    void callback(MovementWithWorkoutMovementHistory movementWithHistory);
}
