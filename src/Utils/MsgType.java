package Utils;

public enum MsgType {
    GRID_UPDATE,   // El servidor envía el grid visible
    SHOT,          // El cliente envía un disparo
    SHOT_RESULT,   // El servidor responde si fue agua o tocado
    GAME_OVER,     // El servidor indica que la partida terminó
    ERROR          // Mensaje de error
}
