export interface Player {
    id: number;
    name: string;
    games: number;
}

export interface Match {
    id: number;
    player1: Player;
    player2: Player;
    score: string;
    state: "ONGOING" | "FINISHED" | "CANCELED";
}