# Insurance Purchase System API

A Spring Boot backend API for managing insurance policies, purchases, and document downloads with personalized recommendations.

## GitHub Repository
[https://github.com/KeshavCh0udhary/LLM_Simplify](https://github.com/KeshavCh0udhary/LLM_Simplify)

## API Endpoints

### Base URL
`/api/insurances`

### Insurance Management
| Method | Endpoint                | Description                          | Parameters/Body                     |
|--------|-------------------------|--------------------------------------|-------------------------------------|
| POST   | `/`                     | Create new insurance policy          | Insurance object in request body    |
| GET    | `/`                     | Get all insurance policies           | None                                |
| GET    | `/type/{type}`          | Get insurances by type               | type: insurance category            |
| GET    | `/{id}`                 | Get insurance by ID                  | id: insurance policy ID             |

### Personalized Features
| Method | Endpoint                | Description                          | Parameters                          |
|--------|-------------------------|--------------------------------------|-------------------------------------|
| GET    | `/recommendations`      | Get recommended insurances           | Optional: age, gender, income<br>OR UserProfile in body |

### Purchase Operations
| Method | Endpoint                | Description                          | Body/Parameters                     |
|--------|-------------------------|--------------------------------------|-------------------------------------|
| POST   | `/{id}/purchase`        | Purchase an insurance policy         | UserProfile object in request body  |

### Document Download
| Method | Endpoint                | Description                          | Notes                               |
|--------|-------------------------|--------------------------------------|-------------------------------------|
| GET    | `/{id}/policy-document` | Download policy document (PDF)       | Requires prior purchase             |

## Request/Response Examples

### Get Recommendations (GET /api/insurances/recommendations)
**Request:**
```json
Content-Type: application/json

{
  "age": 32,
  "gender": "MALE",
  "income": 75000.00
}
```
**Response**
```
[
  {
    "id": 1,
    "type": "Health",
    "name": "Basic Health Insurance",
    "description": "Covers hospitalization expenses",
    "premium": 5000,
    "coverageDetails": null,
    "popularityScore": 0,
    "riskCategory": null,
    "minAge": 0,
    "maxAge": 0,
    "minIncome": 0,
    "policyNumber": null,
    "purchaseDate": null,
    "policyDocumentPath": null,
    "purchased": false,
    "userId": null
  },
  {
    "id": 2,
    "type": "Health",
    "name": "Premium Health Plan",
    "description": "Includes dental and vision coverage",
    "premium": 8000,
    "coverageDetails": null,
    "popularityScore": 0,
    "riskCategory": null,
    "minAge": 0,
    "maxAge": 0,
    "minIncome": 0,
    "policyNumber": null,
    "purchaseDate": null,
    "policyDocumentPath": null,
    "purchased": false,
    "userId": null
  },
  {
    "id": 3,
    "type": "Life",
    "name": "Term Life Insurance",
    "description": "Provides coverage for a fixed term",
    "premium": 6000,
    "coverageDetails": null,
    "popularityScore": 0,
    "riskCategory": null,
    "minAge": 0,
    "maxAge": 0,
    "minIncome": 0,
    "policyNumber": null,
    "purchaseDate": null,
    "policyDocumentPath": null,
    "purchased": false,
    "userId": null
  },
  {
    "id": 4,
    "type": "Life",
    "name": "Whole Life Insurance",
    "description": "Covers life with investment benefits",
    "premium": 12000,
    "coverageDetails": null,
    "popularityScore": 0,
    "riskCategory": null,
    "minAge": 0,
    "maxAge": 0,
    "minIncome": 0,
    "policyNumber": null,
    "purchaseDate": null,
    "policyDocumentPath": null,
    "purchased": false,
    "userId": null
  },
  {
    "id": 11,
    "type": "Health",
    "name": "Gold Health Plan",
    "description": "Top-tier healthcare coverage",
    "premium": 15000,
    "coverageDetails": null,
    "popularityScore": 0,
    "riskCategory": null,
    "minAge": 0,
    "maxAge": 0,
    "minIncome": 0,
    "policyNumber": null,
    "purchaseDate": null,
    "policyDocumentPath": null,
    "purchased": false,
    "userId": null
  }
]
```
