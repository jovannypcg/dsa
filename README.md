# interview-prep

A personal, Claude-assisted repository for preparing for software engineering interviews. It
covers two tracks — coding problems (DSA) and system design — each with its own structured,
test-or-review-driven workflow, plus a set of standalone reference guides.

## Repository Structure

```
.
├── algo/                  # DSA coding problems (Java + Maven)
│   ├── CLAUDE.md          # Workflow rules for this track
│   ├── README.md          # Stack, structure, and the Solved Problems table
│   ├── pom.xml
│   └── src/
│       ├── main/java/mx/jovannypcg/algo/p<NN>_<problem>/
│       └── test/java/mx/jovannypcg/algo/p<NN>_<problem>/
│
├── system-design/         # System design problems (Excalidraw diagrams)
│   ├── CLAUDE.md          # Workflow rules for this track
│   └── p<NN>_<problem>/
│       ├── REQUIREMENTS.md
│       └── DESIGN.excalidraw
│
└── docs/                  # Shared reference guides, not tied to either track
    ├── coding-patterns/   # One file per DSA pattern (recognition, approach, template, curated problems)
    └── design-patterns/   # Creational, structural, and behavioral design patterns
```

## The two tracks

**`algo/`** — one coding problem at a time. Each problem is a numbered subpackage with a
method stub, a problem description, and an exhaustive test suite. Solve it yourself, then
type **"Done"** for a reviewed complexity analysis and alternative approaches, or **"Give
up"** to see the solution. See `algo/CLAUDE.md` for the full workflow and `algo/README.md`
for the current list of solved problems.

**`system-design/`** — the same one-problem-at-a-time approach, adapted for system design.
Each problem gets a requirements doc; you design the architecture yourself as an
`.excalidraw` diagram (plain JSON, versionable in git). Typing **"Done"** or **"Give up"**
has Claude read the diagram's structure directly and produce a review or reference design.
See `system-design/CLAUDE.md` for the full workflow.

## `docs/`

Reference material shared across both tracks (or useful independent of either): pattern
recognition guides for common DSA categories, and write-ups of the classic design patterns.
Worth skimming the relevant guide before starting a new problem.

## Getting Started

Each track's `CLAUDE.md` governs how Claude behaves within that directory — naming
conventions, what gets generated, and what the "Done"/"Give up" signals do. Start a new
problem by asking Claude to set one up in whichever track you want to practice, and it will
follow that track's conventions automatically.
