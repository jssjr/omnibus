%% This tests that the race condition detection between whereis/register
%% is robust w.r.t. having the calls in separate modules (forward analysis).
%% It takes into account control flow that might exist.

-module(whereis_diff_modules3_pathsens).
-export([start/3]).

start(AnAtom, Fun, FunName) ->
  Pid = 
    case FunName of
      master ->
        case whereis(AnAtom) of
          undefined ->
            spawn(Fun);
          P when is_pid(P) ->
            P
        end;
      slave ->
        case whereis(AnAtom) of
          undefined ->
            spawn(Fun);
          P when is_pid(P) ->
            P
        end
    end.
