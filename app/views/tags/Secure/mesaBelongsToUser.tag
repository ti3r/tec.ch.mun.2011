*{Tag that will be used in some pages to see if one mesa has the specified user
as representante. it invokes the mensajeBelongsToUser method on the  controllers.Security
Controller}*
#{if session.username && 
	controllers.Secure.Security.invoke("mesaBelongsToUser", _user, _mesaId)}
    #{doBody /}
#{/if}