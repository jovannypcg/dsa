# System Design

A personal practice space for system design interviews. Each problem is solved end-to-end
inside a single [Excalidraw](https://excalidraw.com) diagram — a real-world system named
plainly up front, worked through on one whiteboard the way an actual interview plays out —
then reviewed or revealed by Claude.

## Tools

- **Diagramming**: [Excalidraw](https://excalidraw.com) (web app). Diagrams are saved as
  `.excalidraw` files — plain JSON, fully versionable in git.
- **Claude-authored diagrams**: [Mermaid](https://mermaid.js.org), used in `SOLUTION.md` and
  optionally in `REVIEW.md`.

## Prerequisites

No build tooling is required — this track is diagram-driven, not code-driven. You'll need:

- An [Excalidraw](https://excalidraw.com) account or the web app open in a browser (an
  Excalidraw+ subscription works too).
- A way to export/download your finished diagram as a `.excalidraw` file from the web app,
  to replace the placeholder file in the problem's directory.

## Repository Structure

This track lives at `system-design/` inside the parent repository:

```
.                                   (repo root)
├── docs/                           # shared reference guides (see below)
├── algo/                           # DSA counterpart, see its own CLAUDE.md
└── system-design/                  # ← this project
    ├── CLAUDE.md
    ├── README.md                   # you are here
    └── p<NNN>_<problemname>/
        ├── README.md                # Problem statement, bootstrapped by Claude
        ├── <problemname>.excalidraw  # The whiteboard — your entire solution lives here
        ├── REVIEW.md                 # Post-solve review (generated on "Done")
        └── SOLUTION.md                # Full solution reveal (generated on "Give up")
```

### Problem naming

Problems are numbered in the order they were attempted, using a three-digit zero-padded
prefix, in its own sequence independent from `algo/`:

```
system-design/p001_uber/
system-design/p002_rate-limiter/
...
```

`<problemname>` is a short, lowercase slug — a single word when the name is one word (e.g.,
`uber`, `pinterest`), hyphen-separated when it's more than one (e.g., `rate-limiter`,
`url-shortener`) — never run together or joined with underscores.

### Files per problem

| File | Purpose |
|------|---------|
| `README.md` | The problem statement. Bootstrapped by Claude; concise, names the real system being modeled (e.g., "Design Uber"). |
| `<problemname>.excalidraw` | An empty placeholder Claude creates, named after the problem. You replace it entirely with your own downloaded `.excalidraw` file — requirements, API design, data model, high-level design, and deep dives all live in this one diagram. |
| `REVIEW.md` | Generated when you type **"Done"**. Assessment of your approach, trade-offs, and a pass/fail verdict against real-interview expectations. |
| `SOLUTION.md` | Generated when you type **"Give up"**. A full reference solution covering every section the diagram was meant to hold, with Mermaid diagrams. |

## Learning Guides (`../docs`)

- `../docs/system-design-patterns/` — one Markdown file per well-known system design pattern
  (load balancing, caching, sharding, replication, consistent hashing, rate limiting,
  message queues, CQRS, circuit breaker, API gateway, CDN, fan-out), each with a Mermaid
  diagram, when to use it, and its trade-offs.

Skim the relevant patterns before starting a problem to prime your thinking on what the
design is likely to need.

## Solved Problems

| # | Problem | Real-World System | Result |
|---|---------|--------------------|--------|

✅ = reviewed (submitted with "Done") · 💡 = solution revealed ("Give up")

## Adding a New Problem with Claude

Tell Claude what to set up:

```
Set up the next system design problem: <real-world system to model>
```

Claude will automatically:
1. Determine the next index by inspecting existing problem directories.
2. Create `README.md` with the problem statement, naming the real-world system plainly.
3. Create the empty placeholder `<problemname>.excalidraw`.

Design your solution in the [Excalidraw web app](https://excalidraw.com), covering
functional/non-functional requirements, API design, data model, high-level design, and deep
dives — ideally organized into named frames (see `CLAUDE.md` for why). When you're done,
download the `.excalidraw` file and replace the placeholder in the problem's directory.

Then type:

- **`Done`** → Claude parses your diagram and writes `REVIEW.md`: your approach, its
  benefits and drawbacks, the functional requirements it addresses, an evaluation, and a
  pass/fail verdict against real-interview expectations.
- **`Give up`** → Claude writes `SOLUTION.md`, a full reference solution covering every
  expected section with Mermaid diagrams.
