# Spyglass

# Endpoints
|| Endpoint | Function |
|------|----------|----------|
|| GET /signin |	Initiates OAuth2 signin process |
|| GET /userinfo | Gets info of logged-in user |
|| POST /logout | Logs out user |
|| GET /goal | Gets the currently logged-in user's goals |
|| GET /goal/all | Gets ALL goals (for testing only) |
|| POST /goal | Creates a new goal, associates it with logged-in user, and returns it |
|| PUT /goal/{id} | Updates goal with specified id |
|| DELETE /goal/{id} | Updates goal with specified id |
