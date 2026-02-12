"use client";

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { matchService } from '@/services/matchService';

export default function Home() {
  const [p1Name, setP1Name] = useState<string>("");
  const [p2Name, setP2Name] = useState<string>("");
  const [isCreating, setIsCreating] = useState<boolean>(false);
  const router = useRouter();

  const capitalize = (s: string) => {
    return s.charAt(0).toUpperCase() + s.slice(1);
  }
    
  const handleStartMatch = async (e: any) => {
    e.preventDefault();
    setIsCreating(true);
    try {
      const match = await matchService.createMatch(p1Name, p2Name);
      router.push(`/game/${match.id}`);
    } catch (err) {
      console.error(err);
      setIsCreating(false);
    }
  };

  return (
    <main className="flex flex-col items-center justify-center min-h-screen bg-bg-main text-text-default">
      <div className="w-full max-w-md p-8 bg-bg-card rounded-3xl border border-border-glow">
        
        <div className="text-center mb-10">
          <h1 className="text-4xl font-black italic text-primary">TENNIS KATA</h1>
          <p className="text-text-muted text-sm mt-2 uppercase">Pong 2.0</p>
        </div>

        <form onSubmit={handleStartMatch} className="space-y-6">
          <div className="space-y-2">
            <label className="text-xs font-bold text-text-muted uppercase">Player 1</label>
            <input 
              required
              type="text"
              value={p1Name}
              onChange={(e) => setP1Name(capitalize(e.target.value))}
              className="w-full bg-bg-input border border-border-subtle p-4 rounded-2xl focus:border-primary outline-none text-text-default"
              placeholder="Name..."
            />
          </div>

          <div className="relative flex items-center justify-center py-2">
            <div className="border-t border-border-subtle w-full"></div>
            <span className="absolute bg-bg-card px-4 text-xs font-black text-text-dim">VS</span>
          </div>

          <div className="space-y-2">
            <label className="text-xs font-bold text-text-muted uppercase">Player 2</label>
            <input 
              required
              type="text"
              value={p2Name}
              onChange={(e) => setP2Name(capitalize(e.target.value))}
              className="w-full bg-bg-input border border-border-subtle p-4 rounded-2xl focus:border-primary outline-none text-text-default"
              placeholder="Name..."
            />
          </div>

          <button 
            type="submit"
            disabled={isCreating}
            className="w-full bg-primary text-black font-black py-5 rounded-2xl hover:bg-primary-hover disabled:opacity-50 transition-colors"
          >
            {isCreating ? "CREATING..." : "START MATCH"}
          </button>
        </form>
      </div>
      <footer className="mt-8 text-text-dim text-[10px] uppercase tracking-[0.2em]">
        Build by Thomas Pauwels
      </footer>
    </main>
  );
}