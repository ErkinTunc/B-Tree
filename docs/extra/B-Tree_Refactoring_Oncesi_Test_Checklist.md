# B-Tree Refactoring Öncesi Test Checklist

**Hedef:** `ArbreB.java` dosyasını refactor etmeden önce mevcut doğru davranışları güvence altına almak, bilinen bug'ları görünür hale getirmek ve refactoring sırasında yeni hatalar oluşmasını engellemek.

**Durum işaretleri:** `[x]` = yazıldı ve JUnit raporunda başarılı olduğu doğrulandı; `[ ]` = henüz tamamlandığı doğrulanmadı.

## 1. Insertion Tests — Anahtar Ekleme

- [x] **Simple insertion:** Boş ağaca tek bir key-value çifti ekle ve `recherche()` ile doğru değerin döndürüldüğünü doğrula. (`simpleInsertionTest`)
- [ ] **Multiple insertions:** Ağaca birden fazla anahtar ekle. Her anahtarın doğru değerle bulunabildiğini doğrula.
- [x] **Unsorted insertions:** Anahtarları sırasız ekle. Ekleme sırasından bağımsız olarak bütün anahtarların doğru değerlerle bulunabildiğini doğrula.
- [x] **Leaf split:** Bir yaprak düğümün kapasitesini aşacak sayıda anahtar ekle. Split sonrasında bütün anahtarların doğru değerlerle bulunabildiğini doğrula.
- [ ] **Internal node split:** İç düğümün de bölünmesini gerektirecek sayıda anahtar ekle. Bölünme sonrasında bütün anahtarların erişilebilir olduğunu doğrula.
- [ ] **Multiple splits:** Birden fazla leaf ve internal node split işlemini tetikle. Önceden eklenen kayıtların kaybolmadığını doğrula.

## 2. Exact Search Tests — Tam Anahtar Arama

- [x] **Missing key:** Test senaryosunda ağaçta bulunmayan bir anahtar arandığında `null` döndürüldüğünü doğrula. (`missingKeyTest`)
- [x] **Empty tree search:** Boş ağaçta arama yapıldığında `null` döndürüldüğünü doğrula.
- [ ] **Existing keys:** Farklı düğümlerde bulunan anahtarların doğru değerlerle bulunabildiğini doğrula.
- [ ] **Search after split:** Leaf veya internal node split sonrasında bütün anahtarların erişilebilir kaldığını doğrula.
- [ ] **Case-sensitive exact search:** Mevcut exact search sözleşmesine göre büyük-küçük harf farklılıklarının nasıl ele alındığını doğrula.

## 3. Interval Search Tests — Aralık Arama

- [x] **Basic interval:** Belirli iki anahtar arasındaki kayıtların doğru döndürüldüğünü doğrula.
- [ ] **Inclusive boundaries:** Aralığın başlangıç ve bitiş anahtarlarının sonuçlara dahil edildiğini doğrula.
- [ ] **Empty interval result:** Verilen aralıkta hiçbir kayıt bulunmadığında boş bir liste döndürüldüğünü doğrula.
- [ ] **Interval across multiple leaves:** Birden fazla yaprak düğümüne yayılan aralıktaki bütün kayıtların bulunduğunu doğrula.
- [ ] **Interval after split:** Düğüm bölünmelerinden sonra aralık aramasının doğru sonuçlar döndürdüğünü doğrula.
- [ ] **Full-range search:** Ağaçtaki bütün kayıtları kapsayan bir aralıkla arama yap ve hiçbir kaydın eksik olmadığını doğrula.
- [ ] **Interval result ordering:** Mevcut API sıralı sonuç döndürmeyi garanti ediyorsa kayıtların anahtar sırasına göre döndürüldüğünü doğrula.
- [ ] **Invalid interval:** Başlangıç anahtarı bitiş anahtarından büyük olduğunda mevcut metodun tanımlanmış davranışını doğrula.

## 4. Prefix Search Tests — Önek Arama

- [x] **Basic prefix:** Belirli bir önekle başlayan anahtarların bulunduğunu doğrula.
- [ ] **Case-insensitive search:** `"pa"` ve `"PA"` gibi öneklerin aynı kayıtları bulduğunu doğrula.
- [ ] **Accent-insensitive search:** `"e"` ve `"é"` gibi aksan farklılıklarının tanımlanmış arama davranışına uygun şekilde ele alındığını doğrula.
- [ ] **No matching prefix:** Hiçbir anahtarla eşleşmeyen önek için boş bir liste döndürüldüğünü doğrula.
- [ ] **Prefix across multiple leaves:** Farklı yaprak düğümlerindeki aynı önekle başlayan bütün kayıtların bulunduğunu doğrula.
- [ ] **Prefix result ordering:** Mevcut API sıralı sonuç döndürmeyi garanti ediyorsa sonuçların beklenen sırada olduğunu doğrula.
- [ ] **Empty prefix:** Boş String (`""`) verildiğinde mevcut metodun tanımlanmış davranışını doğrula.

## 5. Regression Tests — Bilinen Bug'lar

> Refactoring öncesinde beklenen doğru davranışı ifade eden testler yazılmalı. Bu testler mevcut implementation'da başarısız olabilir. Başarısız sonuçları belgeleyin; testleri geçsin diye beklenen davranışı değiştirmeyin.

- [x] **Duplicate key replacement:** Aynı anahtarı farklı değerlerle iki kez ekle. Son eklenen değerin döndürüldüğünü ve ikinci bir mantıksal kayıt oluşmadığını doğrula.
  - Senaryo: `Paris → 75`, ardından `Paris → 75000`.
  - Beklenen: `recherche("Paris") → "75000"`.
- [x] **Stale minKey/maxKey regression:** Child split gerçekleşmeyen insertion sonrasında parent range metadata'sının güncellenmediği senaryoyu yeniden oluştur.
  - Yeni anahtarın exact search ile bulunabildiğini doğrula.
  - Aynı anahtarı kapsayan interval search'ün de kaydı döndürdüğünü doğrula.
  - Yanlış subtree pruning nedeniyle kaydın atlanmadığını kontrol et.

## 6. Structural Invariant Tests — Yapısal Doğruluk

- [ ] **Node capacity:** Hiçbir düğümün izin verilen maksimum anahtar sayısını aşmadığını doğrula.
- [ ] **Key ordering:** Her düğümdeki anahtarların sıralı olduğunu doğrula.
- [ ] **Child ordering:** Internal node anahtarları ile çocuk alt ağaçlarının anahtar aralıklarının tutarlı olduğunu doğrula.
- [ ] **Leaf depth:** Implementation'ın dengeleme kurallarına göre bütün yaprakların aynı derinlikte bulunduğunu doğrula.
- [ ] **Split integrity:** Split sonrasında hiçbir anahtarın veya değerinin kaybolmadığını doğrula.
- [ ] **Range metadata consistency:** `minKey` ve `maxKey` değerlerinin ilgili alt ağacın gerçek anahtar aralığıyla tutarlı olduğunu doğrula.

**Not:** Bu kontrollerin bazıları mevcut public API üzerinden yapılamayabilir. Sırf test yazabilmek için `ArbreB.java` iç yapısını refactoring öncesinde değiştirme. Önce davranışsal testleri tamamla; yapısal kontroller için erişim stratejisini ayrıca belirle.

## 7. Dataset & Integration Tests — Gerçek Veriyle Test

- [ ] **Small dataset loading:** Küçük ve sabit bir test veri kümesini ağaca yükle. Bütün kayıtların bulunabildiğini doğrula.
- [ ] **Dataset lookup:** `communes.txt` dosyasından yüklenen, beklenen değeri bilinen birkaç anahtarı ara ve sonuçları doğrula.
- [ ] **Dataset interval search:** Gerçek veri kümesindeki seçilmiş bir aralık sorgusunu bağımsız hesaplanan beklenen sonuçlarla karşılaştır.
- [ ] **Dataset prefix search:** Gerçek veri kümesindeki aksanlı ve aksansız kayıtlarla prefix search davranışını doğrula.

**Not:** `communes.txt` yükleyerek uygulamanın başarılı çalıştırılması bir otomatik assertion testi değildir. Dataset testleri, küçük ve bağımsız unit testlerden ayrı tutulmalıdır.

---

## Refactoring Öncesi Minimum Kapsam

- [x] Simple insertion + exact search — `simpleInsertionTest` başarılı.
- [x] Missing key — `missingKeyTest` başarılı.
- [x] Leaf split.
- [x] Internal node split.
- [ ] Interval search.
- [ ] Prefix search.
- [ ] Duplicate key replacement regression.
- [ ] Stale `minKey/maxKey` regression.

## Refactoring'e Geçiş Kontrolü

- [ ] Temel davranışları kontrol eden testler başarılı.
- [ ] Leaf ve internal node split otomatik olarak test edildi.
- [ ] Interval ve prefix search için en az birer başarılı test var.
- [ ] Bilinen iki bug için tekrarlanabilir regression testleri yazıldı; mevcut kodda başarısızlarsa beklenen/gerçek farkı belgelendi.
- [ ] Bütün testler `./gradlew test` veya Windows'ta `.\gradlew.bat test` üzerinden çalıştırılabiliyor.
- [ ] Testler konsol çıktısının elle incelenmesine değil, assertion'lara dayanıyor.

**Sıradaki tek görev:** `ArbreB.java` içinde leaf node'un maksimum anahtar kapasitesini ve ilk split'in hangi eklemede gerçekleştiğini belirle; ardından minimum leaf split test girdilerini tasarla.
