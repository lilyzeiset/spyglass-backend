# Spyglass

# Endpoints
| Endpoint | Function |
|----------|----------|
| GET /signin |	Initiates OAuth2 signin process |
| GET /userinfo | Gets info of logged-in user |
| POST /logout | Logs out user |
| GET /goal | Gets all of the currently logged-in user's goals |
| GET /goal/active | Gets the currently logged-in user's active goals |
| GET /goal/inactive | Gets the currently logged-in user's inactive goals (completed or due date passed) |
| GET /goal/all | Gets ALL goals for ALL users (unauthenticated, dev mode only) |
| GET /goal/{id} | Gets a goal by its ID |
| POST /goal/{id}/upload | Receives an image file, uploads it to S3, and updates the goal's imagePath |
| POST /goal | Creates a new goal and associates it with logged-in user |
| PUT /goal/{id} | Updates goal with specified id |
| DELETE /goal/{id} | Updates goal with specified id |
