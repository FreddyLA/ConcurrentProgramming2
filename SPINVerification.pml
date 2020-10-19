#define N 4

int mutex = 1;
int clockwise = 0;
int counterClockwise = 0;

int nClockwise = 0;
int nCounterClockwise = 0;

int dClockwise = 0;
int dCounterClockwise = 0;


inline Signal() {
	if
	:: nClockwise == 0 && dCounterClockwise > 0 ->  dCounterClockwise--;
													V(counterClockwise)
	:: nCounterClockwise == 0 && dClockwise > 0 ->  dClockwise--;
													V(clockwise)
	:: else -> V(mutex)
	fi
}

inline P(X) {
	atomic {X > 0 -> X = X - 1}
} 

inline V(X) { 
	X++
}

active [N] proctype Car()
{
	do
	::	skip;  /* Dummy statement to allow label at entry point */

entry:
		P(mutex);
		if
		:: _pid < N/2 ->
entry1:					if
						:: nClockwise > 0 ->
											dCounterClockwise++;
											V(mutex);
											P(counterClockwise)
						:: else ->
						fi;
crit1:					nCounterClockwise++
		:: else ->
entry2:	 				if
						:: nCounterClockwise > 0 ->
											dClockwise++;
											V(mutex);
											P(clockwise)
						:: else ->
						fi;
crit2: 					nClockwise++
		fi;
		Signal();
		P(mutex);
crit:	if
		:: _pid < N/2 -> nCounterClockwise--
		:: else -> nClockwise--;
		fi;
		Signal()
	od
}

//ltl obl0 {[] ( (Car[0]@entry && [] !(Car[1]@entry)  && [] !(Car[2]@entry) && [] !(Car[3]@entry)) -> <> (Car[0]@crit) )}
ltl res {[] ((Car[0]@entry || Car[1]@entry || Car[2]@entry || Car[3]@entry) -> <> (Car[0]@crit1 || Car[1]@crit1 || Car[2]@crit2 || Car[3]@crit2))}
//ltl fair0 {[] (Car[0]@entry -> <> Car[0]@crit1)}
//ltl fair2 {[] (Car[2]@entry -> <> Car[2]@crit2)}
//ltl safety {[] (nClockwise == 0 || nCounterClockwise == 0)}
