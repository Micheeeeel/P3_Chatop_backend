
package com.chatop.backend.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController

{
	
/*   @RolesAllowed("USER")
   @GetMapping("/*")
   public String getUser()
   {
      return "Welcome User";
   }

   @RolesAllowed({"USER","ADMIN"})
   @GetMapping("/admin")
   public String getAdmin()
   {
      return "Welcome Admin";
   }
   */
   
   @GetMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
}
