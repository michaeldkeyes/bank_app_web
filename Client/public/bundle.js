(()=>{"use strict";const e=()=>document.createElement("form"),n=()=>document.createElement("label"),t=()=>document.createElement("input"),o=()=>document.createElement("button");async function s(e){try{const n=await fetch("http://localhost:7000/customer",{method:"POST",body:JSON.stringify(e)});if(!n.ok)return void console.log(n.status+" "+n);await n,a(content)}catch(e){console.error(e)}}const a=r=>{r.innerHTML="";const c=document.createElement("a"),d=document.createElement("a");c.innerText="Register",d.innerText="Login",c.onclick=()=>{(o=>{o.innerHTML="";const r=e(),c=n();c.innerText="Username";const d=t();d.type="text",d.name="username",d.placeholder="username";const i=n();i.innerText="Password";const p=t();p.type="password",p.name="password",p.placeholder="password";const l=n();l.innerText="Confirm Password";const m=t();m.type="password",m.name="confirmPassword",m.placeholder="confirm password";const u=document.createElement("button");u.type="submit",u.innerText="Register",r.addEventListener("submit",(e=>{!function(e){e.preventDefault();let n=new FormData(e.currentTarget);if(n=Object.fromEntries(n.entries()),(t=n).password===t.confirmPassword){const{username:e,password:t}=n;n={username:e,password:t},s(n)}else alert("Passwords do not match!");var t}(e),a(o)})),r.appendChild(c),r.appendChild(d),r.appendChild(i),r.appendChild(p),r.appendChild(l),r.appendChild(m),r.appendChild(u),o.appendChild(r)})(r)},d.onclick=()=>{(s=>{s.innerHTML="";const a=e(),r=n();r.innerText="Username";const c=t();c.type="text",c.name="username",c.placeholder="username";const d=n();d.innerText="Password";const i=t();i.type="password",i.name="password",i.placeholder="password";const p=o();p.innerText="Login",a.addEventListener("submit",(e=>{!async function(e){e.preventDefault();let n=new FormData(e.currentTarget);n=Object.fromEntries(n.entries());const{username:t,password:o}=n,s=`http://localhost:7000/customer/${t}/${o}`;try{const e=await fetch(s);if(!e.ok){const n=await e.json();return void console.log(n)}const n=await e.json();console.log(n)}catch(e){console.error(e)}}(e)})),a.appendChild(r),a.appendChild(c),a.appendChild(d),a.appendChild(i),a.appendChild(p),s.appendChild(a)})(r)},c.classList.add("m-r-20"),r.appendChild(c),r.appendChild(d)},r=document.getElementById("content");a(r)})();