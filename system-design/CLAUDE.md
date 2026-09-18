# CLAUDE.md (system-design/)

This directory holds system design practice problems for interview preparation, one problem
at a time. Each problem is solved entirely inside a single Excalidraw diagram (`.excalidraw`
— plain JSON, fully versionable in git), the way a real system design interview typically
plays out on one shared whiteboard rather than across several separate documents.

This file governs everything under `system-design/`. It applies independently of any rules
elsewhere in this repository; nothing outside this directory changes how Claude behaves here.

## Naming convention

```
system-design/p<NNN>_<problemname>/
```

- `NNN` is a three-digit zero-padded integer, its own independent sequence starting at `p001`.
- `<problemname>` is a short, lowercase slug. Use a single word when the name is one
  word (e.g., `uber`, `pinterest`); when it's more than one word, separate them with a
  hyphen (e.g., `rate-limiter`, `url-shortener`) — never run them together or use
  underscores.

## Files per problem

| File | Purpose |
|------|---------|
| `README.md` | The problem statement. Bootstrapped by Claude; concise, names the real system being modeled (e.g., "Design Uber"). |
| `<problemname>.excalidraw` | An empty file Claude creates as a placeholder, named after the problem, following the same slug rule as the directory (e.g., `uber.excalidraw`, `rate-limiter.excalidraw`). The solver replaces it entirely with their own downloaded `.excalidraw` file, which holds the whole solution — requirements, API design, data model, high-level design, and deep dives all live in this one diagram. |
| `REVIEW.md` | Generated when the solver types **"Done"**. Assessment of the approach, trade-offs, and a pass/fail verdict against real-interview expectations. |
| `SOLUTION.md` | Generated when the solver types **"Give up"**. A full reference solution covering every section the diagram was meant to hold, with Mermaid diagrams. |

Additionally, `system-design/README.md` (one level up, shared across all problems) tracks
every problem in a "Solved Problems" table. It is not one of the per-problem files above, but
Claude keeps it in sync at every step below.

## Steps to add a problem

1. **Determine the next index** by listing existing subdirectories under `system-design/`
   and incrementing the highest prefix.
2. **Create `README.md`** at `system-design/p<NNN>_<name>/README.md`:
   - A concise, self-contained problem statement, written the way a real interviewer would
     open a session — naming the real-world system being modeled is expected and normal
     (e.g., "Design Uber: Uber is a ride-sharing platform that connects passengers with
     drivers..."). Do not soften or genericize the prompt to hide what it's based on.
   - Include a Mermaid diagram only if it helps clarify the *problem domain* itself — the
     actors involved, or a simple flow of what a user experiences (e.g., "rider requests a
     trip → matched with a nearby driver → trip completes"). Never include anything that
     hints at a system architecture, data model, or API shape — that's the solver's job.
   - Do not include functional or non-functional requirements — the solver defines those
     themselves, inside the diagram.
3. **Create the placeholder diagram** at
   `system-design/p<NNN>_<name>/<name>.excalidraw` — a genuinely empty file (zero bytes,
   no JSON scaffolding). It exists only so the path is there; the solver replaces it
   entirely with the `.excalidraw` file they download from the Excalidraw web app once
   they're done designing, so nothing Claude writes into it would survive anyway.
4. **Add a row to `system-design/README.md`'s "Solved Problems" table** for the new
   problem: index, a link to `p<NNN>_<name>/README.md`, the real-world system named plainly
   (matching what `README.md` names), and 🔄 in the Result column, since it's still in
   progress.
5. **After creating both files**, end with the message:
   > Type **"Done"** when you've finished your diagram in `<name>.excalidraw`, or
   > **"Give up"** if you'd like to see a reference solution.

## Expected structure inside the diagram

The solver is expected to cover, somewhere in the diagram: functional requirements,
non-functional requirements, API design, data model, high-level design, and one or more
deep dives into interesting or hard parts of the system. There's no fixed layout, but using
Excalidraw **frames** (the native "group and label a region of the canvas" feature) named
after these sections — e.g., "Functional Requirements", "API Design", "Data Model",
"High-Level Design", "Deep Dive: Location Updates" — makes the diagram far easier for Claude
to parse accurately on "Done" or reason about on "Give up". Mention this to the solver when
bootstrapping a problem if they haven't used the format before.

## "Done" signal — post-exercise review

When the solver sends **"Done"**, look for `<name>.excalidraw` in the current problem's
directory. If it's missing or still empty (zero bytes), tell the solver and stop.

1. **Read the file as JSON**, not as an image. Parse the `elements` array. If `frame`
   elements are present, group every other element by its `frameId` and use each frame's
   `name` to identify which section it represents (Functional Requirements, API Design,
   Data Model, High-Level Design, deep dives, etc.). Within each group, identify shapes
   (`rectangle`, `ellipse`, `diamond`, `image`) and their bound text labels, and trace
   `arrow` elements via their `startBinding`/`endBinding` to reconstruct connections and
   direction. If no frames are present, do your best to cluster elements spatially and by
   nearby heading-sized text, but call out explicitly in `REVIEW.md` that the diagram wasn't
   organized into labeled sections and note anywhere that ambiguity affected the review —
   never silently guess at structure that isn't there. Freehand strokes and loosely-placed,
   unbound text carry no structural meaning and should be flagged the same way.
2. **Cross-check what you reconstructed against `README.md`.** Note requirements the problem
   statement implies (scale, latency, consistency needs) that the solver's own stated
   requirements or design don't address, and note anything drawn that isn't justified by any
   stated requirement.
3. **Write `REVIEW.md`** in the same directory. Open with a summary table:

   ```markdown
   | | |
   |---|---|
   | **Reviewed on** | YYYY-MM-DD |
   | **Primary pattern** | <e.g., "Fan-out on write", "Consistent hashing", "CQRS"> |
   ```

   Then, in this order:

   ### 1. Approach
   A plain-language walkthrough of what the solver built, section by section (requirements,
   API design, data model, high-level design, deep dives) — grounded in specific components,
   endpoints, and connections you actually identified, not generic praise. Note any of the
   expected sections that are missing entirely.

   ### 2. Benefits and Drawbacks
   What the chosen approach gets right, and where it trades something away (consistency for
   availability, simplicity for scale, cost for latency, etc.) — grounded in the specific
   design, not generic system design commentary.

   ### 3. Functional Requirements Considered
   A list of the functional requirements the solver identified or implied through their
   design, and whether the design actually satisfies each one.

   ### 4. Evaluation
   Correctness and completeness of the API design, data model, and architecture; bottlenecks
   or single points of failure; whether the deep dives address the genuinely hard parts of
   the problem or avoid them. A Mermaid diagram reconstructing the solver's high-level design
   may be included here if it makes the evaluation clearer, but it isn't required.

   ### 5. Verdict
   A direct, honest answer: would this design, presented as-is, pass a real system design
   interview at a typical tech company? State what would sink it or what would make it
   stand out, not just a restated summary of the sections above.
4. **Update the problem's row in `system-design/README.md`'s "Solved Problems" table**:
   set the Result column to ✅.

## "Give up" signal — solution reveal

When the solver sends **"Give up"**, write `SOLUTION.md` in the problem's directory:

```markdown
| | |
|---|---|
| **Created on** | YYYY-MM-DD |
| **Primary pattern** | <category> |
```

Cover every section the diagram was meant to hold, end to end: functional requirements,
non-functional requirements, API design, data model, high-level design, and deep dives into
the problem's genuinely hard parts. Use well-formatted Mermaid for every diagram —
`flowchart` for the high-level architecture, `sequenceDiagram` for request flows,
`erDiagram` for the data model — fenced with ` ```mermaid `. Keep explanations tight: the
goal is that reading `SOLUTION.md` alone is enough to understand the design immediately,
without needing to open any diagram file.

Finally, **update the problem's row in `system-design/README.md`'s "Solved Problems"
table**: set the Result column to 💡.

## Diagrams

The solver's own design lives in `.excalidraw` (never Mermaid, ASCII art, or an embedded
image — that file is their whiteboard). Diagrams Claude generates for the solver, in
`SOLUTION.md`, are Mermaid, fenced with ` ```mermaid ` — never ASCII art or an external image
link. Diagrams Claude generates for itself inside `REVIEW.md` (optional, as noted above) are
also Mermaid.

## What Claude should NOT do

- Do not add content to `<name>.excalidraw` at any point — creating the empty placeholder file is
  the only time Claude touches that file. Filling it in is the solver's work alone.
- Do not include functional/non-functional requirements, API design, a data model, or any
  other solution content in `README.md` — it is the problem statement only.
- Do not soften or hide the real-world system a problem is modeled after — naming it plainly
  in `README.md` is expected.
- Do not create or modify any files outside `system-design/`.
- Do not modify any part of `system-design/README.md` other than the "Solved Problems"
  table — its prose (structure, naming convention, workflow explanation) is maintained by
  hand, not regenerated by Claude.
