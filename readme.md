Scoped Key Space Construction Proof-of-Concept
----------------------------------------------

This project contains a very rough, very simplistic proof-of-concept implementation of DITA 1.3
keyspace construction using scopes as describedin
[Proposal 13004](https://www.oasis-open.org/committees/download.php/48898/1-3proposal-13004.html).
Using lots of cheats, shortcuts, and simplifying assumptions, it nonetheless can generate a description
of of the key spaces in a key map specifying the @keyscope attributes.

You can either run it in stanadlone mode using the Main class or run all of the test files using the
junit test case. The output of the junit test case is below. The test maps include all of the meaningful
examples from the proposal, as well as the TractorX example from the e-mail thread, and Eliot Kimber's
cross-publication linking proof-of-concept.

KEY SPACES FOR: /Example2.1.ditamap
===================================

ROOT (anonymous)
----------------
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

Tractor-X
---------
* ProductName: Tractor X
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

Tractor-Y
---------
* ProductName: Tractor Y
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

************************************************************************

KEY SPACES FOR: /Example2.2.ditamap
===================================

ROOT (anonymous)
----------------
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

Tractor-X
---------
* ProductName: Tractor X
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

Tractor-Y
---------
* ProductName: Tractor Y
* Tractor-X.ProductName: Tractor X
* Tractor-Y.ProductName: Tractor Y

************************************************************************

KEY SPACES FOR: /Example3.ditamap
=================================

ROOT (anonymous)
----------------
* scope-1.key-1: someTopic.dita

scope-1
-------
* key-1: someTopic.dita
* scope-1.key-1: someTopic.dita

************************************************************************

KEY SPACES FOR: /Example4.ditamap
=================================

ROOT (anonymous)
----------------
* scope-1.scope-1-key-1: Topic1.dita
* scope-2.scope-2-key-1: Topic2.dita

scope-1
-------
* scope-1-key-1: Topic1.dita
* scope-1.scope-1-key-1: Topic1.dita
* scope-2.scope-2-key-1: Topic2.dita

scope-2
-------
* scope-1.scope-1-key-1: Topic1.dita
* scope-2-key-1: Topic2.dita
* scope-2.scope-2-key-1: Topic2.dita

************************************************************************

KEY SPACES FOR: /Example5/map-1.ditamap
=======================================

ROOT (anonymous)
----------------
* key-1: @id="keydef-3"
* scope-1.key-1: @id="keydef-1"
* scope-1.key-2: @id="keydef-2"

scope-1
-------
* key-1: @id="keydef-3"
* key-2: @id="keydef-2"
* scope-1.key-1: @id="keydef-1"
* scope-1.key-2: @id="keydef-2"

************************************************************************

KEY SPACES FOR: /Example6.ditamap
=================================

ROOT (anonymous)
----------------
* child.keyName: @id="keydef1"

child
-----
* child.keyName: @id="keydef1"
* keyName: @id="keydef2"

************************************************************************

KEY SPACES FOR: /Example7/parent.ditamap
========================================

ROOT (anonymous)
----------------
* mapScopeName1.key-1: @id="theDefinition"
* mapScopeName2.key-1: @id="theDefinition"
* maprefScopeName1.key-1: @id="theDefinition"
* maprefScopeName2.key-1: @id="theDefinition"

maprefScopeName1 (and other names too)
--------------------------------------
* key-1: @id="theDefinition"
* mapScopeName1.key-1: @id="theDefinition"
* mapScopeName2.key-1: @id="theDefinition"
* maprefScopeName1.key-1: @id="theDefinition"
* maprefScopeName2.key-1: @id="theDefinition"

************************************************************************

KEY SPACES FOR: /Tractors/NoScopes/AllModels.ditamap
====================================================

ROOT (anonymous)
----------------
* TractorX.OilChart: TractorX/oilChart.dita
* TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.OilChart: TractorY/oilChart.dita
* TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

************************************************************************

KEY SPACES FOR: /Tractors/BrokenScopes/AllModels.ditamap
========================================================

ROOT (anonymous)
----------------
* TractorX.TractorX.OilChart: TractorX/oilChart.dita
* TractorX.TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.TractorY.OilChart: TractorY/oilChart.dita
* TractorY.TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

TractorX
--------
* TractorX.OilChart: TractorX/oilChart.dita
* TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorX.TractorX.OilChart: TractorX/oilChart.dita
* TractorX.TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.TractorY.OilChart: TractorY/oilChart.dita
* TractorY.TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

TractorY
--------
* TractorX.TractorX.OilChart: TractorX/oilChart.dita
* TractorX.TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.OilChart: TractorY/oilChart.dita
* TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita
* TractorY.TractorY.OilChart: TractorY/oilChart.dita
* TractorY.TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

************************************************************************

KEY SPACES FOR: /Tractors/FixedScopes/AllModels.ditamap
=======================================================

ROOT (anonymous)
----------------
* TractorX.OilChart: TractorX/oilChart.dita
* TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.OilChart: TractorY/oilChart.dita
* TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

TractorX
--------
* OilChart: TractorX/oilChart.dita
* RegularMaintenance: TractorX/regularMaintenance.dita
* TractorX.OilChart: TractorX/oilChart.dita
* TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.OilChart: TractorY/oilChart.dita
* TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

TractorY
--------
* OilChart: TractorY/oilChart.dita
* RegularMaintenance: TractorY/RegularMaintenance.dita
* TractorX.OilChart: TractorX/oilChart.dita
* TractorX.RegularMaintenance: TractorX/regularMaintenance.dita
* TractorY.OilChart: TractorY/oilChart.dita
* TractorY.RegularMaintenance: TractorY/RegularMaintenance.dita

************************************************************************

KEY SPACES FOR: /CrossPubs/map-a/map-a.ditamap
==============================================

ROOT (anonymous)
----------------
* map-b.topic-b-01: ../topics/topic-b-01.dita (peer)
* map-b.topic-b-02: ../topics/topic-b-02.dita (peer)
* map-b.topic-b-root: ../topics/topic-b-root.dita (peer)
* topic-a-01: ../topics/topic-a-01.dita
* topic-a-02: ../topics/topic-a-02.dita
* topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* topic-a-root: ../topics/topic-a-root.dita

************************************************************************

KEY SPACES FOR: /CrossPubs/map-b/map-b.ditamap
==============================================

ROOT (anonymous)
----------------
* map-a.topic-a-01: ../topics/topic-a-01.dita (peer)
* map-a.topic-a-02: ../topics/topic-a-02.dita (peer)
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita (peer)
* map-a.topic-a-root: ../topics/topic-a-root.dita (peer)
* topic-b-01: ../topics/topic-b-01.dita
* topic-b-02: ../topics/topic-b-02.dita
* topic-b-root: ../topics/topic-b-root.dita

************************************************************************

KEY SPACES FOR: /CrossPubs/map-ab/map-ab-omnibus.ditamap
========================================================

ROOT (anonymous)
----------------
* map-a.map-b.topic-b-01: ../topics/topic-b-01.dita
* map-a.map-b.topic-b-02: ../topics/topic-b-02.dita
* map-a.map-b.topic-b-root: ../topics/topic-b-root.dita
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.map-a.topic-a-01: ../topics/topic-a-01.dita
* map-b.map-a.topic-a-02: ../topics/topic-a-02.dita
* map-b.map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-b.map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita

map-a
-----
* map-a.map-b.topic-b-01: ../topics/topic-b-01.dita
* map-a.map-b.topic-b-02: ../topics/topic-b-02.dita
* map-a.map-b.topic-b-root: ../topics/topic-b-root.dita
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.map-a.topic-a-01: ../topics/topic-a-01.dita
* map-b.map-a.topic-a-02: ../topics/topic-a-02.dita
* map-b.map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-b.map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-a-01: ../topics/topic-a-01.dita
* topic-a-02: ../topics/topic-a-02.dita
* topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* topic-a-root: ../topics/topic-a-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita

map-b
-----
* map-a.map-b.topic-b-01: ../topics/topic-b-01.dita
* map-a.map-b.topic-b-02: ../topics/topic-b-02.dita
* map-a.map-b.topic-b-root: ../topics/topic-b-root.dita
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.map-a.topic-a-01: ../topics/topic-a-01.dita
* map-b.map-a.topic-a-02: ../topics/topic-a-02.dita
* map-b.map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-b.map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita
* topic-b-01: ../topics/topic-b-01.dita
* topic-b-02: ../topics/topic-b-02.dita
* topic-b-root: ../topics/topic-b-root.dita

************************************************************************

KEY SPACES FOR: /CrossPubs/map-ab/map-ab-selective-reuse.ditamap
================================================================

ROOT (anonymous)
----------------
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita

map-a
-----
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-a-01: ../topics/topic-a-01.dita
* topic-a-02: ../topics/topic-a-02.dita
* topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* topic-a-root: ../topics/topic-a-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita

map-b
-----
* map-a.topic-a-01: ../topics/topic-a-01.dita
* map-a.topic-a-02: ../topics/topic-a-02.dita
* map-a.topic-a-peer-mapref-root: ../topics/topic-a-peer-mapref-root.dita
* map-a.topic-a-root: ../topics/topic-a-root.dita
* map-b.topic-b-01: ../topics/topic-b-01.dita
* map-b.topic-b-02: ../topics/topic-b-02.dita
* map-b.topic-b-root: ../topics/topic-b-root.dita
* topic-ab-01: ../topics/topic-ab-01.dita
* topic-ab-02: ../topics/topic-ab-02.dita
* topic-ab-root: ../topics/topic-ab-root.dita
* topic-b-01: ../topics/topic-b-01.dita
* topic-b-02: ../topics/topic-b-02.dita
* topic-b-root: ../topics/topic-b-root.dita

************************************************************************

