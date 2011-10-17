*{Tag that will be used in some pages to see if one mensaje has the specified user
as autor. it invokes the mesaBelongsToUser method on the  controllers.Security
Controller}*
#{if session.username && 
	controllers.Secure.Security.invoke("mensajeBelongsToUser", _user, _mensajeId)}
    #{doBody /}
#{/if}