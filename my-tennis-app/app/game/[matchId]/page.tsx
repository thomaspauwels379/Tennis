"use client";

import { useEffect, useRef, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { matchService } from '@/services/matchService';
import { Match } from '@/types';
import GameEngine from '@/components/GameEngine';

export default function GamePage() {
  const { matchId } = useParams();
  const router = useRouter();
  
  const [match, setMatch] = useState<Match | null>(null);
  const [loading, setLoading] = useState(true);
  

  const keysRef = useRef<Record<string, boolean>>({});

  useEffect(() => {
    const handleDown = (e: KeyboardEvent) => {
      keysRef.current[e.code] = true;
    };
    const handleUp = (e: KeyboardEvent) => {
      keysRef.current[e.code] = false;
    };
    window.addEventListener('keydown', handleDown);
    window.addEventListener('keyup', handleUp);

    return () => {
      window.removeEventListener('keydown', handleDown);
      window.removeEventListener('keyup', handleUp);
    };
  }, []);

  const getMatch = async () => {
    try {
      if (!matchId) return;
      const data = await matchService.getMatch(matchId as string);
      setMatch(data);
    } catch (err) {
      console.error("Fout bij ophalen match:", err);
    } finally {
      setLoading(false);
    }
  };

  const cancelMatch = () => {
    matchService.cancelMatch(matchId as string);
    () => router.push('/')
  }

  useEffect(() => {
    getMatch();
    const interval = setInterval(getMatch, 2000);
    return () => clearInterval(interval);
  }, [matchId]);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen bg-bg-main">
        <div className="text-primary font-mono animate-pulse uppercase tracking-widest text-sm">
          Connecting...
        </div>
      </div>
    );
  }

  if (!match) return null;

  return (
    <main
     className="flex flex-col h-screen max-h-screen bg-bg-main p-4 md:p-6 overflow-hidden text-text-default"
     onKeyUp={(e) => keysRef.current[e.code] = false}>
      
      <div className="w-full max-w-4xl mx-auto bg-bg-card rounded-2xl border border-border-glow p-4 mb-4 shrink-0">
        <div className="grid grid-cols-3 items-center">
          
          <div className="text-center">
            <h2 className="text-lg md:text-xl font-black italic truncate px-2 uppercase">{match.player1.name}</h2>
            <p className="text-[9px] text-text-muted font-bold uppercase tracking-tighter">Player 1</p>
          </div>

          <div className="flex flex-col items-center border-x border-border-subtle px-2">
            <div className="flex items-center gap-4 md:gap-8">
              <div className="text-2xl md:text-3xl font-bold text-text-muted tabular-nums">
                {match.player1.games}
              </div>

              <div className="text-4xl md:text-3xl font-black text-primary italic tabular-nums leading-none tracking-tight">
                {match.score}
              </div>

              <div className="text-2xl md:text-3xl font-bold text-text-muted tabular-nums">
                {match.player2.games}
              </div>
            </div>

            <span className={`mt-2 px-2 py-0.5 rounded text-[8px] font-black uppercase tracking-widest ${
              match.state === 'FINISHED' ? 'bg-error text-white' : 'text-text-dim border border-border-subtle'
            }`}>
              {match.state}
            </span>
          </div>

          <div className="text-center">
            <h2 className="text-lg md:text-xl font-black italic truncate px-2 uppercase">{match.player2.name}</h2>
            <p className="text-[9px] text-text-muted font-bold uppercase tracking-tighter">Player 2</p>
          </div>
        </div>
      </div>

      <div className="grow w-full max-w-5xl mx-auto bg-black rounded-xl border border-border-subtle relative overflow-hidden flex items-center justify-center">
        <div className="absolute inset-y-0 left-1/2 w-px border-r border-dashed border-white/10"></div>
        {match.state == "ONGOING" && (
          <GameEngine keysPressed={keysRef} match={match}/>
        )}
        {match.state == "FINISHED" && (
          <div className="z-10 text-center animate-in fade-in zoom-in duration-500">
            <h1 className="text-7xl md:text-9xl font-black italic text-white mb-2 uppercase tracking-tighter drop-shadow-2xl">
              Match Finished
            </h1>
            <p className="text-primary font-mono text-xl uppercase tracking-widest">
              Winner: {match.player1.games > match.player2.games ? match.player1.name : match.player2.name}
            </p>
            <button 
              onClick={() => router.push('/')}
              className="mt-8 px-8 py-3 bg-primary text-black font-black uppercase italic rounded-full hover:scale-105 transition-transform"
            >
              Back to Menu
            </button>
          </div>
        )}
      </div>

      <div className="flex justify-between items-center w-full max-w-5xl mx-auto mt-4 shrink-0">
        <button 
          onClick={cancelMatch}
          className="text-text-dim hover:text-text-muted text-[10px] uppercase tracking-widest transition-colors flex items-center gap-2"
        >
          <span className="text-xs">←</span> Quit Session
        </button>
        
        <div className="text-[9px] text-text-dim font-mono uppercase tracking-tighter">
          Match ID: {matchId?.toString()}
        </div>
      </div>
    </main>
  );
}