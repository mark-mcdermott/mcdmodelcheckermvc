grammar CtlCompiler;

@header {
    package controller.ctlCompiler;
}

formula
    : not
    | conj
    | imp
    | temp
    | bool
    | atom
    ;

not
    : '¬' Φ1=formula
    ;

conj
    : and
    | or
    ;

and
    : '∧(' Φ1=formula ',' Φ2=formula ')'
    ;

or
    : '∨(' Φ1=formula ',' Φ2=formula ')'
    ;

imp
    : '→(' Φ1=formula ',' Φ2=formula ')'
    ;

temp
    : ax
    | ex
    | af
    | ef
    | ag
    | eg
    | au
    | eu
    ;

ax
    : 'AX(' Φ1=formula ')'
    | 'AX' Φ1=formula
    ;

ex
    : 'EX(' Φ1=formula ')'
    | 'EX' Φ1=formula
    ;

af
    : 'AF(' Φ1=formula ')'
    | 'AF' Φ1=formula
    ;

ef
    : 'EF(' Φ1=formula ')'
    | 'EF' Φ1=formula
    ;

ag
    : 'AG(' Φ1=formula ')'
    | 'AG' Φ1=formula
    ;

eg
    : 'EG(' Φ1=formula ')'
    | 'EG' Φ1=formula
    ;

au
    : 'A[' Φ1=formula 'U' Φ2=formula ']'
    ;

eu
    : 'E[' Φ1=formula 'U' Φ2=formula ']'
    ;

atom
    : LABEL
    ;

bool
    : taut
    | cont
    ;

taut
    : '⊤'
    ;

cont
    : '⊥'
    ;

LABEL
    : [p-z]
    ;
