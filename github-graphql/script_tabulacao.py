import requests
import pandas as pd

access_token = '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'

def run_query(query):
    """Executa uma query GraphQL na API do GitHub."""
    url = 'https://api.github.com/graphql'
    headers = {'Authorization': f'Bearer {access_token}'}
    response = requests.post(url, json={'query': query}, headers=headers)
    if response.status_code != 200:
        raise RuntimeError(
            f"Falha na execução da query GraphQL.\n"
            f"Status HTTP: {response.status_code}\n"
            f"Resposta: {response.text}\n"
            f"Query: {query}"
        )
    data = response.json()
    if 'errors' in data:
        raise RuntimeError(
            f"Erros retornados pela API GraphQL:\n"
            f"{data['errors']}\n"
            f"Query: {query}"
        )
    return data

def query_composer(cursor=None):
    """Cria uma query GraphQL para buscar Pull Requests."""
    cursor_part = f', after: "{cursor}"' if cursor else ""
    query = f"""
    query {{
        search(query: "chat.openai.com/share is:pr is:merged in:title,body", type: ISSUE, first: 100{cursor_part}) {{
            pageInfo {{
                endCursor
                hasNextPage
            }}
            issueCount
            edges {{
                node {{
                    ... on PullRequest {{
                        url
                        title
                        createdAt
                        mergedAt
                        repository {{
                            stargazerCount
                            isFork
                            primaryLanguage {{
                                name
                            }}
                        }}
                    }}
                }}
            }}
        }}
    }}
    """
    return query

def get_samples():
    """Busca os dados dos Pull Requests e retorna como uma lista de dicionários."""
    cursor = None
    has_next_page = True
    prs = []

    while has_next_page:
        result = run_query(query_composer(cursor))
        end_cursor = result["data"]["search"]["pageInfo"]["endCursor"]
        has_next_page = result["data"]["search"]["pageInfo"]["hasNextPage"]
        issue_count = result["data"]["search"]["issueCount"]
        print(f"Occurrences: {issue_count}")

        for pr in result["data"]["search"]["edges"]:
            node = pr["node"]
            repository = node["repository"]
            prs.append({
                "PR URL": node["url"],
                "PR Title": node["title"],
                "PR CreatedAt": node["createdAt"],
                "PR MergedAt": node["mergedAt"],
                "Stars": repository["stargazerCount"],
                "Fork": repository["isFork"],
                "Language": repository["primaryLanguage"]["name"] if repository["primaryLanguage"] else ""
            })

        cursor = end_cursor

    return prs

def write_samples_to_csv(prs, filename='Candidate_samples.csv'):
    """Salva os dados dos Pull Requests em um arquivo CSV usando pandas."""
    df = pd.DataFrame(prs)
    df.to_csv(filename, index=False, encoding='utf-8')

# Fluxo principal
prs = get_samples()
write_samples_to_csv(prs)
