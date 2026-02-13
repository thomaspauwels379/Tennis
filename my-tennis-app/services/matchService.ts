import { Match } from "@/types";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/matches";

export const matchService = {

    async getOngoingMatch(id: string | number): Promise<Match> {
        const response = await fetch(BASE_URL, {
            method:'GET',
            cache: 'no-store',
        });

        if (!response.ok) throw new Error("Match niet gevonden");
        return await response.json();
    },

    async getMatch(id: string | number): Promise<Match> {
        const response = await fetch(BASE_URL + "/" + id, {
            method:'GET',
        });

        if (!response.ok) throw new Error("Match niet gevonden");
        return await response.json();
    },

    async createMatch(p1Name: string, p2Name: string): Promise<Match> {
        const response = await fetch(`${BASE_URL}?namePlayer1=${encodeURIComponent(p1Name)}&namePlayer2=${encodeURIComponent(p2Name)}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
        });

        if (!response.ok) throw new Error("Fout bij aanmaken match");
        return await response.json();
    },

    async scorePoint(matchId: number, playerId: number): Promise<Match> {
        const response = await fetch(`${BASE_URL}/${matchId}/score?playerId=${playerId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
        });

        if (!response.ok) throw new Error("Kon punt niet registreren");
        return await response.json();
    },

    async cancelMatch(id: string): Promise<void> {
        const response = await fetch(`${BASE_URL}/${id}/cancel`, {
            method: 'POST',
        });
        if (!response.ok) throw new Error("Match annuleren mislukt");
    }
}