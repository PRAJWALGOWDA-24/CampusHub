package com.prajwal.campushub.security;

public class JwtAuthenticationEntryPoint {
}


/*no work for this class ,
It is an empty class.

It is doing nothing.

Normally Spring Security returns

403 Forbidden

or

401 Unauthorized

with ugly HTML.

Instead,

people create

JwtAuthenticationEntryPoint

to return nice JSON like

{
  "message":"Token expired",
  "status":401
}

instead of

Whitelabel Error Page*/

/*
Do you need it?
For my  project?

No.

Because you already handle it in JavaScript.

You wrote

checkSession(response)


 implement it properly later.

 */